package com.imo.backend.modules.course.services;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.http.dtos.UpdateCourseByIdRequest;

public interface UpdateCourseByIdService {
  Course execute(String courseId, UpdateCourseByIdRequest fieldsToUpdateCourse);
}
