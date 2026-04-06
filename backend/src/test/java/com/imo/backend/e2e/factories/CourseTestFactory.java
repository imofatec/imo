package com.imo.backend.e2e.factories;

import com.imo.backend.contexts.catalog.course.http.dtos.CreateCourseRequest;
import com.imo.backend.contexts.catalog.lesson.http.dtos.CreateLessonRequest;
import com.imo.backend.e2e.utils.DataFakeFactory;
import java.util.ArrayList;
import java.util.List;

public class CourseTestFactory {

  public static CreateCourseRequest createValidCourseWithLessons(int index) {

    List<CreateLessonRequest> lessons = new ArrayList<>();
    for (int i = 0; i < index; i++) {
      var lesson =
          new CreateLessonRequest(
              DataFakeFactory.generateValidTitle(),
              DataFakeFactory.generateValidDescription() + " - " + i,
              DataFakeFactory.generateValidYoutubeLink());
      lessons.add(lesson);
    }

    return new CreateCourseRequest(
        DataFakeFactory.generateValidCourseName(),
        DataFakeFactory.generateValidCourseCategory(),
        DataFakeFactory.generateValidCourseLevel(),
        DataFakeFactory.generateValidCourseDescription(),
        lessons,
        DataFakeFactory.generateValidId());
  }
}
