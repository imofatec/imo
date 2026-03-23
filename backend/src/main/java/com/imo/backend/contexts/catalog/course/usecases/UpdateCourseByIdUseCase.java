package com.imo.backend.contexts.catalog.course.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.CoursePolicies;
import com.imo.backend.contexts.catalog.course.commands.UpdateCourseByIdCommand;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.common.Slug;
import org.springframework.stereotype.Service;

@Service
public class UpdateCourseByIdUseCase {

  private final CourseRepository courseRepository;
  private final CoursePolicies coursePolicies;

  public UpdateCourseByIdUseCase(CourseRepository courseRepository, CoursePolicies coursePolicies) {
    this.courseRepository = courseRepository;
    this.coursePolicies = coursePolicies;
  }

  public Course execute(String courseId, UpdateCourseByIdCommand cmd) {
    Course course = this.courseRepository.findByIdOrThrow(courseId);

    if (cmd.name() != null) {
      this.coursePolicies.checkUpdateConflict(
          courseId,
          course.getContributorId(),
          Slug.create(cmd.name())
      );
    }

    Course.applyUpdate(course, cmd);

    return this.courseRepository.save(course);
  }
}
