package com.imo.backend.contexts.catalog.course.usecases.impl;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.usecases.IncLessonsCountByIdUseCase;

import org.springframework.stereotype.Service;

@Service
public class IncLessonsCountByIdUseCaseImpl implements IncLessonsCountByIdUseCase {
  private final CourseRepository courseRepository;

  public IncLessonsCountByIdUseCaseImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public void execute(String id) {
    Course course = this.courseRepository.findByIdOrThrow(id);
    course.incrementLessonsCount();
    this.courseRepository.save(course);
  }
}
