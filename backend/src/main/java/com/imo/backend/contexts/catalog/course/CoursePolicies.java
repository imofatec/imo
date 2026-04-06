package com.imo.backend.contexts.catalog.course;

import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import org.springframework.stereotype.Component;

@Component
public class CoursePolicies {

  private final CourseRepository courseRepository;

  public CoursePolicies(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  public void checkUpdateConflict(String courseId, String contributorId, String newSlug) {
    boolean existingContributorCourse =
        this.courseRepository.findAllByContributorId(contributorId).stream()
            .anyMatch(
                course ->
                    course.getName().slug().equals(newSlug) && !course.getId().equals(courseId));

    if (existingContributorCourse) {
      throw new ConflictException(String.format("Curso %s já existe", newSlug));
    }
  }
}
