package com.imo.backend.config.mongodb.populate;

import com.imo.backend.contexts.catalog.course.http.dtos.CreateCourseRequest;
import com.imo.backend.contexts.catalog.course.orchestrators.CreateCourseOrchestrator;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.lesson.http.dtos.CreateLessonRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import net.datafaker.Faker;
import org.springframework.stereotype.Service;

@Service
public class PopulateCourses {
  private final Faker faker = new Faker();

  private final CreateCourseOrchestrator createCourseOrchestrator;

  public PopulateCourses(CreateCourseOrchestrator createCourseOrchestrator) {
    this.createCourseOrchestrator = createCourseOrchestrator;
  }

  public void execute(String contributorId, int qyt) {
    for (int i = 0; i < qyt; i++) {
      List<CreateLessonRequest> lessons = new ArrayList<>();

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
              faker.options().option(Categories.class),
              faker.options().option("Iniciante", "Intermediário", "Avançado"),
              faker.lorem().characters(10, 300),
              lessons,
              contributorId);

      this.createCourseOrchestrator.execute(createCourseRequest, contributorId);
    }
  }

  private int generateBiasedLessonsQty(int min, int max) {
    double biasFactor = 2.5;
    double rand = Math.pow(ThreadLocalRandom.current().nextDouble(), biasFactor);
    return (int) (min + (max - min) * rand);
  }
}
