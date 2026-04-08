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

  public void checkSlugConflict(String contributorId, String newSlug, String currentCourseId) {
    boolean existingContributorCourse =
        this.courseRepository.findAllByContributorId(contributorId).stream()
            .anyMatch(
                course ->
                    course.getName().slug().equals(newSlug)
                        && (currentCourseId == null || !course.getId().equals(currentCourseId)));

    if (existingContributorCourse) {
      throw new ConflictException(String.format("Curso %s já existe", newSlug));
    }
  }
}
