package com.imo.backend.modules.course.actions.impl;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.actions.UpdateCourseByIdAction;
import com.imo.backend.modules.course.actions.helpers.CourseUpdater;
import com.imo.backend.modules.course.actions.inputs.UpdateCourseByIdInput;
import com.imo.backend.modules.course.repositories.CourseRepository;
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
