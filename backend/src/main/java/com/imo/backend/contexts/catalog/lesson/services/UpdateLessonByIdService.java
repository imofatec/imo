package com.imo.backend.contexts.catalog.lesson.services;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.actions.inputs.UpdateLessonInput;

public interface UpdateLessonByIdService {
  Lesson execute(String lessonId, UpdateLessonInput input);
}
