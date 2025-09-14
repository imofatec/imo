package com.imo.backend.modules.course.actions.impl;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.actions.CreateCourseAction;
import com.imo.backend.modules.course.actions.inputs.CreateCourseInput;
import com.imo.backend.modules.course.factories.CourseFactory;
import com.imo.backend.modules.course.repositories.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCourseActionImpl implements CreateCourseAction {
  private final CourseRepository courseRepository;

  public CreateCourseActionImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public Course execute(CreateCourseInput input) {
    return this.courseRepository.save(CourseFactory.createCourse(input));
  }
}
