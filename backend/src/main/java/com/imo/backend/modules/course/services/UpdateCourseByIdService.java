package com.imo.backend.modules.course.services;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.actions.inputs.UpdateCourseInput;

public interface UpdateCourseByIdService {
  Course execute(String courseId, UpdateCourseInput fieldsToUpdateCourse);
}
