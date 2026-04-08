package com.imo.backend.contexts.catalog.lesson;

import com.imo.backend.contexts.catalog.lesson.commands.CreateLessonCommand;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LessonFactory {
  public static List<Lesson> createLesson(List<CreateLessonCommand> commands, String courseId) {
    return IntStream.range(0, commands.size()).mapToObj(i -> {
      CreateLessonCommand item = commands.get(i);
      Lesson lesson = new Lesson(courseId, i + 1, item.title(), item.description(), item.youtubeLink());
      return lesson;
    }).collect(Collectors.toList());
  }
}
