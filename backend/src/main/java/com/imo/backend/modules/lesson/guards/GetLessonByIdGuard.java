package com.imo.backend.modules.lesson.guards;

import com.imo.backend.modules.lesson.Lesson;

public interface GetLessonByIdGuard {
  Lesson execute(String lessonId);
}
