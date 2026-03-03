package com.imo.backend.contexts.catalog.course.guards.impl;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.guards.GetCoursesByContributorIdGuard;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.common.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCoursesByContributorIdGuardImpl implements GetCoursesByContributorIdGuard {
  private final CourseRepository courseRepository;

  public GetCoursesByContributorIdGuardImpl(CourseRepository courseRepository) {
    this.courseRepository = courseRepository;
  }

  @Override
  public List<Course> execute(String contributorId) {
    return this.courseRepository.findAllByContributorId(contributorId);
  }

  @Override
  public List<Course> execute(String contributorId, int page, int size) {
    return this.courseRepository.findAllByContributorId(
        contributorId,
        Pageable.fromPageSize(page, size)
    );
  }
}
