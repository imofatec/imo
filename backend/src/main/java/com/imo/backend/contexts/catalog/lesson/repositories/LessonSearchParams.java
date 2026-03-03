package com.imo.backend.contexts.catalog.lesson.repositories;

public record LessonSearchParams(
    String courseName,
    String courseNameSlug
) {
}
