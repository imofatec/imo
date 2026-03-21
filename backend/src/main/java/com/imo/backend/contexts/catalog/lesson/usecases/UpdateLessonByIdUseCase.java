package com.imo.backend.contexts.catalog.lesson.usecases;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.commands.UpdateLessonCommand;
import com.imo.backend.contexts.catalog.lesson.http.dtos.UpdateLessonRequest;

public interface UpdateLessonByIdUseCase {
  Lesson execute(String lessonId, UpdateLessonRequest request);

  Lesson execute(UpdateLessonCommand command);
}
