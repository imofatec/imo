package com.imo.backend.contexts.catalog.lesson.guards;

import com.imo.backend.contexts.catalog.lesson.Lesson;

public interface GetLessonByIdGuard {
  Lesson execute(String lessonId);
}
