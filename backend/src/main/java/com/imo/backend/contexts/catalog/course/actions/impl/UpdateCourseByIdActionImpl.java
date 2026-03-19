package com.imo.backend.contexts.catalog.course.actions.impl;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.UpdateCourseByIdAction;
import com.imo.backend.contexts.catalog.course.actions.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;

import org.springframework.stereotype.Service;

@Service
public class UpdateCourseByIdActionImpl implements UpdateCourseByIdAction {
  private final CourseRepository courseRepository;

  public UpdateCourseByIdActionImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Course execute(UpdateCourseByIdCommand command) {
    Course course = courseRepository
        .findById(command.id())
        .orElseThrow(() -> new NotFoundException("Curso não encontrado"));
        
    Course.applyUpdate(course, command);
    return this.courseRepository.save(course);
  }
}
