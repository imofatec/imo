package com.imo.backend.modules.course.guards;

import com.imo.backend.modules.course.Course;

import java.util.List;

public interface GetCoursesByContributorIdGuard {
  List<Course> execute(String contributorId);

  List<Course> execute(String contributorId, int page, int size);
}
