package com.imo.backend.contexts.catalog.course.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import org.springframework.stereotype.Service;

@Service
public class ToggleCourseStatusByIdUseCase {

  private final CourseRepository courseRepository;

  public ToggleCourseStatusByIdUseCase(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  public Course execute(String id) {
    var foundCourse = courseRepository.findByIdOrThrow(id);

    foundCourse.toggleStatus();
    return this.courseRepository.save(foundCourse);
  }
}
