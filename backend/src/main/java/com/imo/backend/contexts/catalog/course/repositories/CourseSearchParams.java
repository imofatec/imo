package com.imo.backend.contexts.catalog.course.repositories;

public record CourseSearchParams(
    String name, String nameSlug, String levelSlug, String categorySlug, String contributorId) {}
