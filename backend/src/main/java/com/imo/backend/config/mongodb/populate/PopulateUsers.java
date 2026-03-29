package com.imo.backend.config.mongodb.populate;

import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.commands.UpdateUserByIdCommand;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import com.imo.backend.contexts.identity.user.usecases.UpdateUserByIdUseCase;
import com.imo.backend.contexts.identity.user.value_objects.AcademicDegree;
import com.imo.backend.contexts.identity.user.value_objects.AvailableTimePerDay;
import com.imo.backend.contexts.identity.user.value_objects.ExperienceLevel;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PopulateUsers {
  private final Faker faker = new Faker();
  private final UserRepository userRepository;
  private final UpdateUserByIdUseCase updateUserByIdUseCase;

  public PopulateUsers(UserRepository userRepository, UpdateUserByIdUseCase updateUserByIdUseCase) {
    this.userRepository = userRepository;
    this.updateUserByIdUseCase = updateUserByIdUseCase;
  }

  public User execute(int qty) {
    WeightedRandom<AvailableTimePerDay> availableTimeGenerator = new WeightedRandom<>(
        List.of(
            AvailableTimePerDay.LESS_THAN_ONE_HOUR,
            AvailableTimePerDay.ONE_TO_TWO_HOURS,
            AvailableTimePerDay.TWO_TO_FOUR_HOURS,
            AvailableTimePerDay.MORE_THAN_FOUR_HOURS
        ), List.of(0.2, 0.4, 0.3, 0.1)
    );

    // senha = admin
    User admin = new User(
        "admin",
        "admin@admin.com",
        "$2a$12$q2.hFPm72fMWRfxSvm1JRu.C3L6gxzDR3BjpKCXtD3cTED.6iXiha",
        true
    );
    this.userRepository.save(admin);

    for (int i = 0; i < qty; i++) {
      String password = faker.internet().password(8, 16, true, true);
      User input = new User(
          faker.name().firstName(),
          faker.internet().emailAddress(),
          password,
          false
      );

      User newUser = this.userRepository.save(input);

      int age = generateBiasedAge(18, 60);
      Date birthDate = getBirthDateFromAge(age);
      LocalDate parsedBirthDate = birthDate
          .toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDate();

      AcademicDegree academicDegree = generateDegreeBasedOnAge(age);
      ExperienceLevel experienceLevel = correlatedExperience(academicDegree);
      AvailableTimePerDay availableTime = availableTimeGenerator.next();

      List<Categories> categoriesOfInterest = new ArrayList<>();
      var firstCategory = faker.options().option(Categories.class);
      var secondCategory = faker.options().option(Categories.class);
      while (firstCategory == secondCategory) {
        secondCategory = faker.options().option(Categories.class);
      }
      categoriesOfInterest.add(firstCategory);
      categoriesOfInterest.add(secondCategory);

      UpdateUserByIdCommand cmd = new UpdateUserByIdCommand(
          null,
          null,
          null,
          null,
          parsedBirthDate,
          availableTime,
          academicDegree,
          experienceLevel,
          categoriesOfInterest
      );

      this.updateUserByIdUseCase.execute(newUser.getId(), cmd);
    }

    return admin;
  }

  private int generateBiasedAge(int min, int max) {
    double biasFactor = 2.5;
    double rand = Math.pow(ThreadLocalRandom.current().nextDouble(), biasFactor);
    return (int) (min + (max - min) * rand);
  }

  private Date getBirthDateFromAge(int age) {
    var now = new Date();
    var currentYear = now.toInstant().atZone(ZoneId.systemDefault()).getYear();
    var birthYear = currentYear - age;
    int month = ThreadLocalRandom.current().nextInt(1, 13);
    int day = ThreadLocalRandom.current().nextInt(1, 28);
    return Date.from(java.time.LocalDate
        .of(birthYear, month, day)
        .atStartOfDay(ZoneId.systemDefault())
        .toInstant());
  }

  private AcademicDegree generateDegreeBasedOnAge(int age) {
    if (age < 18) {
      return AcademicDegree.NONE;
    }
    if (age < 20) {
      return faker.options().option(AcademicDegree.TECHNICAL, AcademicDegree.ASSOCIATE);
    }
    if (age < 25) {
      return faker.options().option(AcademicDegree.BACHELOR, AcademicDegree.LICENTIATE);
    }
    if (age < 30) {
      return faker.options().option(AcademicDegree.LICENTIATE, AcademicDegree.MBA);
    }
    if (age < 40) {
      return faker.options().option(AcademicDegree.MASTER, AcademicDegree.MBA);
    }
    if (age < 55) {
      return faker.options().option(AcademicDegree.MASTER, AcademicDegree.DOCTORAL);
    }
    return faker.options().option(AcademicDegree.DOCTORAL, AcademicDegree.POSTDOC);
  }

  private ExperienceLevel correlatedExperience(AcademicDegree degree) {
    return switch (degree) {
      case NONE, TECHNICAL -> new WeightedRandom<>(
          List.of(
              ExperienceLevel.BEGINNER,
              ExperienceLevel.INTERMEDIATE,
              ExperienceLevel.ADVANCED,
              ExperienceLevel.EXPERT
          ), List.of(0.6, 0.3, 0.1, 0.0)
      ).next();

      case ASSOCIATE, BACHELOR, LICENTIATE -> new WeightedRandom<>(
          List.of(
              ExperienceLevel.BEGINNER,
              ExperienceLevel.INTERMEDIATE,
              ExperienceLevel.ADVANCED,
              ExperienceLevel.EXPERT
          ), List.of(0.2, 0.45, 0.25, 0.1)
      ).next();

      case MBA, MASTER -> new WeightedRandom<>(
          List.of(
              ExperienceLevel.BEGINNER,
              ExperienceLevel.INTERMEDIATE,
              ExperienceLevel.ADVANCED,
              ExperienceLevel.EXPERT
          ), List.of(0.05, 0.25, 0.45, 0.25)
      ).next();

      case DOCTORAL, POSTDOC -> new WeightedRandom<>(
          List.of(
              ExperienceLevel.BEGINNER,
              ExperienceLevel.INTERMEDIATE,
              ExperienceLevel.ADVANCED,
              ExperienceLevel.EXPERT
          ), List.of(0.0, 0.1, 0.35, 0.55)
      ).next();
    };
  }
}
