package com.imo.backend.models.course.services.get.courses.interfaces;

import com.imo.backend.models.course.Course;

public interface GetCourseByIdService {
    Course execute(String courseId);
}
