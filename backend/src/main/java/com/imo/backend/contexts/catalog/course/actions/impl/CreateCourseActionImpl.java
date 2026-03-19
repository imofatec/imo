package com.imo.backend.contexts.catalog.course.actions.impl;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.CreateCourseAction;
import com.imo.backend.contexts.catalog.course.actions.commands.CreateCourseCommand;
import com.imo.backend.contexts.catalog.course.factories.CourseFactory;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCourseActionImpl implements CreateCourseAction {
  private final CourseRepository courseRepository;

  public CreateCourseActionImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Course execute(CreateCourseCommand command) {
    return this.courseRepository.save(CourseFactory.createCourse(command));
  }
}
