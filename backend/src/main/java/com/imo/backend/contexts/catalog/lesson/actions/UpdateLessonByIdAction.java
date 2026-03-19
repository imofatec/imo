package com.imo.backend.contexts.catalog.lesson.actions;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.commands.UpdateLessonCommand;
public interface UpdateLessonByIdAction {
  Lesson execute(UpdateLessonCommand command);
}
