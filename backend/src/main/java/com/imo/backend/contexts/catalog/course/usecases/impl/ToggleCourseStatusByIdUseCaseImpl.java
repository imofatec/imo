package com.imo.backend.contexts.catalog.course.usecases.impl;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.usecases.ToggleCourseStatusByIdUseCase;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class ToggleCourseStatusByIdUseCaseImpl implements ToggleCourseStatusByIdUseCase {

  private final CourseRepository courseRepository;

  public ToggleCourseStatusByIdUseCaseImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Course execute(String id) {
    var foundCourse =
        courseRepository
            .findById(id)
            .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

    foundCourse.toggleStatus();
    return this.courseRepository.save(foundCourse);
  }
}
