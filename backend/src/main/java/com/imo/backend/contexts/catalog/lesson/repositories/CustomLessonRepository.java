package com.imo.backend.contexts.catalog.lesson.repositories;

import com.imo.backend.contexts.catalog.lesson.Lesson;

import java.util.List;

public interface CustomLessonRepository {
  List<Lesson> findAllByCourseId(String courseId);

}
