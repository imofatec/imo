package com.imo.backend.models.course.repositories;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.FieldsToUpdateCourse;

public interface CustomCourseRepository {

    Course updateCourse(String token, String courseId, FieldsToUpdateCourse fieldsToUpdateCourse);
}