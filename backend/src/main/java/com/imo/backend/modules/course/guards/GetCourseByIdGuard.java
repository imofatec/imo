package com.imo.backend.modules.course.guards;

import com.imo.backend.modules.course.Course;

public interface GetCourseByIdGuard {
  Course execute(String courseId);
}
