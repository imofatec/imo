package com.imo.backend.config.mongodb.populate;

import com.imo.backend.contexts.catalog.course.Categories;
import com.imo.backend.contexts.catalog.course.http.dtos.CreateCourseRequest;
import com.imo.backend.contexts.catalog.course.usecases.CreateCourseUseCase;
import com.imo.backend.contexts.catalog.lesson.http.dtos.CreateLessonRequest;
import com.imo.backend.contexts.catalog.skill.repositories.SkillRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

@Service
public class PopulateCourses {
  private final Faker faker = new Faker();

  private final CreateCourseUseCase createCourseUseCase;
  private final SkillRepository skillRepository;

  public PopulateCourses(CreateCourseUseCase createCourseUseCase, SkillRepository skillRepository) {
    this.createCourseUseCase = createCourseUseCase;
    this.skillRepository = skillRepository;
  }

  public void execute(String contributorId, int qyt) {
    for (int i = 0; i < qyt; i++) {
      List<CreateLessonRequest> lessons = new ArrayList<>();
      Categories category = faker.options().option(Categories.class);

      for (int j = 0; j < this.generateBiasedLessonsQty(3, 10); j++) {
        String videoId = faker.regexify("[\\w-]{11}");
        var youtubeLink = "https://www.youtube.com/watch?v=" + videoId;

        CreateLessonRequest createLessonInput =
            new CreateLessonRequest(
                faker.lorem().characters(10, 50), faker.lorem().characters(10, 300), youtubeLink);
        lessons.add(createLessonInput);
      }

      CreateCourseRequest createCourseRequest =
          new CreateCourseRequest(
              faker.lorem().characters(10, 100),
              category,
              faker.options().option("Iniciante", "Intermediário", "Avançado"),
              faker.lorem().characters(10, 300),
              lessons,
              this.pickSkillIds(category));

      this.createCourseUseCase.execute(createCourseRequest.toCommand(contributorId));
    }
  }

  private int generateBiasedLessonsQty(int min, int max) {
    double biasFactor = 2.5;
    double rand = Math.pow(ThreadLocalRandom.current().nextDouble(), biasFactor);
    return (int) (min + (max - min) * rand);
  }

  private List<String> pickSkillIds(Categories category) {
    List<String> categorySkillIds =
        this.skillRepository.findAll().stream()
            .filter(skill -> skill.getCategory().name().equals(category.getValue()))
            .map(skill -> skill.getId())
            .toList();

    List<String> shuffledSkillIds = new ArrayList<>(categorySkillIds);
    Collections.shuffle(shuffledSkillIds);

    int maxSkills = Math.min(2, shuffledSkillIds.size());
    int skillsCount = ThreadLocalRandom.current().nextInt(1, maxSkills + 1);
    return shuffledSkillIds.subList(0, skillsCount);
  }
}
