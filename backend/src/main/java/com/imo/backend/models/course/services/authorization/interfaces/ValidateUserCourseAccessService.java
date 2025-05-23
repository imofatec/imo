package com.imo.backend.models.course.services.authorization.interfaces;

public interface ValidateUserCourseAccessService {
  void execute(String userId, String courseId);
}
