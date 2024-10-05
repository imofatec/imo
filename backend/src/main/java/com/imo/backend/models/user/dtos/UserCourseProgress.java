package com.imo.backend.models.user.dtos;

import com.imo.backend.models.course.dtos.CourseProgress;

public record UserCourseProgress(String id, String username, String email, CourseProgress courseProgress) {
}
