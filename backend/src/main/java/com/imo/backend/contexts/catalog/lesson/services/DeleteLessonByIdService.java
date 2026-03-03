package com.imo.backend.contexts.catalog.lesson.services;

import com.imo.backend.contexts.catalog.lesson.Lesson;

public interface DeleteLessonByIdService {
  Lesson execute(String lessonId);
}
