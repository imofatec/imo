package com.imo.backend.models.course.services.update.interfaces;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.FieldsToUpdateCourse;

public interface UpdateCourseService {
    Course execute(String token, String courseId, FieldsToUpdateCourse fieldsToUpdateCourse);

}
