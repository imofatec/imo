package com.imo.backend.contexts.catalog.lesson.actions.factory;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.commands.CreateLessonCommand;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LessonFactory {
  public static List<Lesson> createLesson(List<CreateLessonCommand> commands, String courseId) {
    return IntStream.range(0, commands.size())
        .mapToObj(
            i -> {
              CreateLessonCommand item = commands.get(i);
              Lesson lesson = new Lesson();
              lesson.setCourseId(courseId);
              lesson.setIndexInCourse(i + 1);
              lesson.setTitle(item.title());
              lesson.setDescription(item.description());
              lesson.setYoutubeLink(item.youtubeLink());
              return lesson;
            })
        .collect(Collectors.toList());
  }

  public static Lesson createLesson(
      CreateLessonCommand command, int indexInCourse, String courseId) {
    Lesson lesson = new Lesson();
    lesson.setCourseId(courseId);
    lesson.setIndexInCourse(indexInCourse);
    lesson.setTitle(command.title());
    lesson.setDescription(command.description());
    lesson.setYoutubeLink(command.youtubeLink());
    return lesson;
  }
}
