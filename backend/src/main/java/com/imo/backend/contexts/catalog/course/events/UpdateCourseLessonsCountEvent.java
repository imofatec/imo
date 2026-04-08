package com.imo.backend.contexts.catalog.course.events;

public record UpdateCourseLessonsCountEvent(String courseId, int newCount) {}
