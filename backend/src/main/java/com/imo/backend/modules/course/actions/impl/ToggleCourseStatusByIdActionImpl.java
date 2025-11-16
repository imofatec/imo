package com.imo.backend.modules.course.actions.impl;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.actions.ToggleCourseStatusByIdAction;
import com.imo.backend.modules.course.repositories.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class ToggleCourseStatusByIdActionImpl implements ToggleCourseStatusByIdAction {

  private final CourseRepository courseRepository;

  public ToggleCourseStatusByIdActionImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Course execute(String id) {
    var foundCourse = courseRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Curso não encontrado"));
    return this.courseRepository.toggleStatusById(id, foundCourse.isActive());
  }
}
