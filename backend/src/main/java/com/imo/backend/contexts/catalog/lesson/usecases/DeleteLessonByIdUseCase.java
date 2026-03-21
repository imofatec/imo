package com.imo.backend.contexts.catalog.lesson.usecases;

import com.imo.backend.contexts.catalog.lesson.Lesson;

public interface DeleteLessonByIdUseCase {
  Lesson execute(String lessonId);
}
