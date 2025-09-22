package com.imo.backend.modules.course.http.middlewares;

public interface ValidateUserCourseAccessService {
  void execute(String userId, String courseId);
}
