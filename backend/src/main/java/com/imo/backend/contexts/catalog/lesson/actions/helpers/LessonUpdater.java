package com.imo.backend.contexts.catalog.lesson.actions.helpers;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.commands.UpdateLessonCommand;
public class LessonUpdater {
  public static void apply(Lesson lesson, UpdateLessonCommand command) {
    if (command.title() != null) {
      lesson.setTitle(command.title());
    }

    if (command.youtubeLink() != null) {
      lesson.setYoutubeLink(Lesson.formatYoutubeLink(command.youtubeLink()));
    }

    if (command.description() != null) {
      lesson.setDescription(command.description());
    }
  }
}
