package com.imo.backend.contexts.catalog.course.usecases.impl;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.commands.CreateCourseCommand;
import com.imo.backend.contexts.catalog.course.factories.CourseFactory;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.usecases.CreateCourseUseCase;

import org.springframework.stereotype.Service;

@Service
public class CreateCourseUseCaseImpl implements CreateCourseUseCase {
  private final CourseRepository courseRepository;

  public CreateCourseUseCaseImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Course execute(CreateCourseCommand command) {
    return this.courseRepository.save(CourseFactory.createCourse(command));
  }
}
