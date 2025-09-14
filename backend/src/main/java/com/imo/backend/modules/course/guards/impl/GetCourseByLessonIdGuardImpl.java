package com.imo.backend.modules.course.guards.impl;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.guards.GetCourseByLessonIdGuard;
import com.imo.backend.modules.course.repositories.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCourseByLessonIdGuardImpl implements GetCourseByLessonIdGuard {
  private final CourseRepository courseRepository;

  public GetCourseByLessonIdGuardImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Course execute(String lessonId) {
    return this.courseRepository
        .findByLessonId(lessonId)
        .orElseThrow(() -> new NotFoundException("Curso não encontrado"));
  }
}
