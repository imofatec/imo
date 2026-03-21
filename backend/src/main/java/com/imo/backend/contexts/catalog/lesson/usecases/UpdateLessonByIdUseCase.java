package com.imo.backend.contexts.catalog.lesson.usecases;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.commands.UpdateLessonCommand;

public interface UpdateLessonByIdUseCase {
  Lesson execute(UpdateLessonCommand command);
}
