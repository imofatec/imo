package com.imo.backend.modules.lesson.services;

import com.imo.backend.modules.lesson.Lesson;

public interface DeleteLessonByIdService {
  Lesson execute(String lessonId);
}
