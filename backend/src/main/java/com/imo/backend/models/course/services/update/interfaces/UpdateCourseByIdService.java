package com.imo.backend.models.course.services.update.interfaces;

import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.dtos.FieldsToUpdateCourse;

public interface UpdateCourseByIdService {
  CourseOverview execute(String courseId, FieldsToUpdateCourse fieldsToUpdateCourse);
}
