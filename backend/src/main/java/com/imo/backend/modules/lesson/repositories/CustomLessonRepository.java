package com.imo.backend.modules.lesson.repositories;

import com.imo.backend.modules.lesson.Lesson;

import java.util.List;

public interface CustomLessonRepository {
  List<Lesson> findAllByCourseId(String courseId);

}
