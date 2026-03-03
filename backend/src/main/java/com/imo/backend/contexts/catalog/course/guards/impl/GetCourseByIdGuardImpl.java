package com.imo.backend.contexts.catalog.course.guards.impl;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.guards.GetCourseByIdGuard;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCourseByIdGuardImpl implements GetCourseByIdGuard {

  private final CourseRepository courseRepository;

  public GetCourseByIdGuardImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Course execute(String courseId) {
    return courseRepository
        .findById(courseId)
        .orElseThrow(() -> new NotFoundException("Curso nao encontrado"));
  }
}
