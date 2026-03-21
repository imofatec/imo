package com.imo.backend.contexts.catalog.course.usecases.impl;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.actions.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.http.dtos.UpdateCourseByIdRequest;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.usecases.UpdateCourseByIdUseCase;
import com.imo.backend.contexts.common.Slug;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;

import org.springframework.stereotype.Service;

@Service
public class UpdateCourseByIdUseCaseImpl implements UpdateCourseByIdUseCase {

  private final CourseRepository courseRepository;

  public UpdateCourseByIdUseCaseImpl(
      CourseRepository courseRepository
  ) {
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

    UpdateCourseByIdCommand command = fieldsToUpdateCourse.toCommand(courseId);

    return this.execute(command);
  }
  @Override 
  public Course execute(UpdateCourseByIdCommand command) {
    Course course = this.courseRepository
      .findById(command.id())
      .orElseThrow(() -> new NotFoundException("Curso não encontrado"));
  
      Course.applyUpdate(course, command);
      return this.courseRepository.save(course);
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
