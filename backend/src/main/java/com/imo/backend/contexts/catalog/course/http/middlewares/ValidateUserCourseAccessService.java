package com.imo.backend.contexts.catalog.course.http.middlewares;

public interface ValidateUserCourseAccessService {
  void execute(String userId, String courseId);
}
