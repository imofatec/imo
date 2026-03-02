package com.imo.backend.modules.course.services.impl;

import com.imo.backend.exceptions.custom.ConflictException;
import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.actions.UpdateCourseByIdAction;
import com.imo.backend.modules.course.actions.inputs.UpdateCourseByIdInput;
import com.imo.backend.modules.course.guards.GetCourseByIdGuard;
import com.imo.backend.modules.course.guards.GetCoursesByContributorIdGuard;
import com.imo.backend.modules.course.http.dtos.UpdateCourseByIdRequest;
import com.imo.backend.modules.course.services.UpdateCourseByIdService;
import com.imo.backend.utils.Slug;
import org.springframework.stereotype.Service;

@Service
public class UpdateCourseByIdServiceImpl implements UpdateCourseByIdService {
  private final UpdateCourseByIdAction updateCourseByIdAction;

  private final GetCourseByIdGuard getCourseByIdGuard;

  private final GetCoursesByContributorIdGuard getCoursesByContributorIdGuard;

  public UpdateCourseByIdServiceImpl(
      UpdateCourseByIdAction updateCourseByIdAction,
      GetCourseByIdGuard getCourseByIdGuard,
      GetCoursesByContributorIdGuard getCoursesByContributorIdGuard
  ) {
    this.updateCourseByIdAction = updateCourseByIdAction;
    this.getCourseByIdGuard = getCourseByIdGuard;
    this.getCoursesByContributorIdGuard = getCoursesByContributorIdGuard;
  }

  @Override
  public Course execute(String courseId, UpdateCourseByIdRequest fieldsToUpdateCourse) {
    Course course = this.getCourseByIdGuard.execute(courseId);

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
    var existingContributorCourse = this.getCoursesByContributorIdGuard
        .execute(contributorId)
        .stream()
        .anyMatch(course -> course.getName().slug().equals(maybeNewSlug) && !course
            .getId()
            .equals(courseId));

    if (existingContributorCourse) {
      throw new ConflictException(String.format("Curso %s já existe", maybeNewSlug));
    }
  }
}
