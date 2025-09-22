package com.imo.backend.modules.course.events;

public record UpdateCourseLessonsCountEvent(
    String courseId,
    int newCount
) {
}
