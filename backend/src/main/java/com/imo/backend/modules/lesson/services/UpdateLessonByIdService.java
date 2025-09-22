package com.imo.backend.modules.lesson.services;

import com.imo.backend.modules.lesson.Lesson;
import com.imo.backend.modules.lesson.actions.inputs.UpdateLessonInput;

public interface UpdateLessonByIdService {
  Lesson execute(String lessonId, UpdateLessonInput input);
}
