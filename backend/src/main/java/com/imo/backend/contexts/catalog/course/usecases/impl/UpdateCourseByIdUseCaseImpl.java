package com.imo.backend.contexts.catalog.course.usecases.impl;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.CoursePolicies;
import com.imo.backend.contexts.catalog.course.actions.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.http.dtos.UpdateCourseByIdRequest;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.course.usecases.UpdateCourseByIdUseCase;
import com.imo.backend.contexts.common.Slug;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;

import org.springframework.stereotype.Service;

@Service
public class UpdateCourseByIdUseCaseImpl implements UpdateCourseByIdUseCase {

  private final CourseRepository courseRepository;
  private final CoursePolicies coursePolicies; 

  public UpdateCourseByIdUseCaseImpl(
      CourseRepository courseRepository,
      CoursePolicies coursePolicies
  ) {
    this.courseRepository = courseRepository;
    this.coursePolicies = coursePolicies;
  }

  @Override
  public Course execute(String courseId, UpdateCourseByIdRequest fieldsToUpdateCourse) {
    Course course = this.courseRepository.findByIdOrThrow(courseId);

    if (fieldsToUpdateCourse.name() != null) {
      this.coursePolicies.checkUpdateConflict(
        courseId,
        course.getContributorId(),
        Slug.create(fieldsToUpdateCourse.name()));
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
   
}
