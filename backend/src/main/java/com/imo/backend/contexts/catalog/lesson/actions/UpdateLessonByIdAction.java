package com.imo.backend.contexts.catalog.lesson.actions;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.inputs.UpdateLessonInput;

public interface UpdateLessonByIdAction {
  Lesson execute(String id, UpdateLessonInput fieldsToUpdateLesson);
}
