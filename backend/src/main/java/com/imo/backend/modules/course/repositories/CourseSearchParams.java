package com.imo.backend.modules.course.repositories;

public record CourseSearchParams(
    String name,
    String nameSlug,
    String levelSlug,
    String categorySlug,
    String contributorId
) {
}
