package com.imo.backend.contexts.catalog.lesson.services;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.commands.UpdateLessonCommand;

public interface UpdateLessonByIdService {
  Lesson execute(UpdateLessonCommand command);
}
