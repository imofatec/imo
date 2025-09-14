package com.imo.backend.modules.lesson.actions.factory;

import com.imo.backend.modules.lesson.Lesson;
import com.imo.backend.modules.lesson.actions.inputs.CreateLessonInput;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LessonFactory {
  public static List<Lesson> createLesson(
      List<CreateLessonInput> createLessonInput,
      String courseId
  ) {
    return IntStream.range(0, createLessonInput.size()).mapToObj(i -> {
      CreateLessonInput item = createLessonInput.get(i);
      Lesson lesson = new Lesson();
      lesson.setCourseId(courseId);
      lesson.setIndexInCourse(i + 1);
      lesson.setTitle(item.title());
      lesson.setDescription(item.description());
      lesson.setYoutubeLink(item.youtubeLink());
      return lesson;
    }).collect(Collectors.toList());
  }

  public static Lesson createLesson(
      CreateLessonInput createLessonInput,
      int indexInCourse,
      String courseId
  ) {
    Lesson lesson = new Lesson();
    lesson.setCourseId(courseId);
    lesson.setIndexInCourse(indexInCourse);
    lesson.setTitle(createLessonInput.title());
    lesson.setDescription(createLessonInput.description());
    lesson.setYoutubeLink(createLessonInput.youtubeLink());
    return lesson;
  }
}
