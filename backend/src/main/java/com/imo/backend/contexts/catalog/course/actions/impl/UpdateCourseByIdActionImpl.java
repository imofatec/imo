package com.imo.backend.contexts.catalog.course.actions.impl;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.UpdateCourseByIdAction;
import com.imo.backend.contexts.catalog.course.actions.helpers.CourseUpdater;
import com.imo.backend.contexts.catalog.course.actions.inputs.UpdateCourseByIdInput;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateCourseByIdActionImpl implements UpdateCourseByIdAction {
  private final CourseRepository courseRepository;

  public UpdateCourseByIdActionImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Course execute(String courseId, UpdateCourseByIdInput fieldsToUpdateCourse) {
    Course course = courseRepository
        .findById(courseId)
        .orElseThrow(() -> new NotFoundException("Curso não encontrado"));
    CourseUpdater.apply(course, fieldsToUpdateCourse);
    return this.courseRepository.save(course);
  }
}
