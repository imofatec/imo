package com.imo.backend.contexts.catalog.course.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.events.CourseStatusToggledEvent;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class ToggleCourseStatusByIdUseCase {

  private final CourseRepository courseRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  public ToggleCourseStatusByIdUseCase(
      CourseRepository courseRepository, ApplicationEventPublisher applicationEventPublisher) {
    this.courseRepository = courseRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public Course execute(String id) {
    var foundCourse = courseRepository.findByIdOrThrow(id);

    foundCourse.toggleStatus();
    Course savedCourse = this.courseRepository.save(foundCourse);
    this.applicationEventPublisher.publishEvent(
        new CourseStatusToggledEvent(
            savedCourse.getId(), savedCourse.getSkillIdsAsString(), savedCourse.isActive()));

    return savedCourse;
  }
}
