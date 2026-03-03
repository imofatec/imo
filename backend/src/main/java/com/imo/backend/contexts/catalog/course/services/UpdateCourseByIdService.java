package com.imo.backend.contexts.catalog.course.services;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.http.dtos.UpdateCourseByIdRequest;

public interface UpdateCourseByIdService {
  Course execute(String courseId, UpdateCourseByIdRequest fieldsToUpdateCourse);
}
