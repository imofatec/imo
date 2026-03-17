package com.imo.backend.contexts.catalog.course.services.impl;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.UpdateCourseByIdAction;
import com.imo.backend.contexts.catalog.course.actions.inputs.UpdateCourseByIdInput;
import com.imo.backend.contexts.catalog.course.http.dtos.UpdateCourseByIdRequest;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.services.UpdateCourseByIdService;
import com.imo.backend.contexts.common.Slug;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import org.springframework.stereotype.Service;

@Service
public class UpdateCourseByIdServiceImpl implements UpdateCourseByIdService {
  private final UpdateCourseByIdAction updateCourseByIdAction;

  private final CourseRepository courseRepository;

  public UpdateCourseByIdServiceImpl(
      UpdateCourseByIdAction updateCourseByIdAction,
      CourseRepository courseRepository
  ) {
    this.updateCourseByIdAction = updateCourseByIdAction;
    this.courseRepository = courseRepository;
  }

  @Override
  public Course execute(String courseId, UpdateCourseByIdRequest fieldsToUpdateCourse) {
    Course course = this.courseRepository.findByIdOrThrow(courseId);

    if (fieldsToUpdateCourse.name() != null) {
      this.checkConflictContributorCourse(
          courseId,
          course.getContributorId(),
          Slug.create(fieldsToUpdateCourse.name())
      );
    }

    UpdateCourseByIdInput input = new UpdateCourseByIdInput(
        fieldsToUpdateCourse.name(),
        fieldsToUpdateCourse.category(),
        fieldsToUpdateCourse.level(),
        fieldsToUpdateCourse.description(),
        null,
        null
    );
    return this.updateCourseByIdAction.execute(courseId, input);
  }

  private void checkConflictContributorCourse(
      String courseId,
      String contributorId,
      String maybeNewSlug
  ) {
    var existingContributorCourse = this.courseRepository.findAllByContributorId(contributorId)
        .stream()
        .anyMatch(course -> course.getName().slug().equals(maybeNewSlug) && !course
            .getId()
            .equals(courseId));

    if (existingContributorCourse) {
      throw new ConflictException(String.format("Curso %s já existe", maybeNewSlug));
    }
  }
}
