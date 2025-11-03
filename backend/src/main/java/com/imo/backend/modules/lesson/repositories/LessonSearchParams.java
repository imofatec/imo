package com.imo.backend.modules.lesson.repositories;

public record LessonSearchParams(
    String courseName,
    String courseNameSlug
) {
}
