package com.imo.backend.contexts.catalog.lesson.actions.helpers;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.inputs.UpdateLessonInput;

public class LessonUpdater {
  public static void apply(Lesson lesson, UpdateLessonInput dto) {
    if (dto.title() != null) {
      lesson.setTitle(dto.title());
    }

    if (dto.youtubeLink() != null) {
      lesson.setYoutubeLink(Lesson.formatYoutubeLink(dto.youtubeLink()));
    }

    if (dto.description() != null) {
      lesson.setDescription(dto.description());
    }
  }
}
