package com.imo.backend.contexts.catalog.course.guards;

import com.imo.backend.contexts.catalog.course.Course;

import java.util.List;

public interface GetCoursesByContributorIdGuard {
  List<Course> execute(String contributorId);

  List<Course> execute(String contributorId, int page, int size);
}
