package com.imo.backend.modules.course.repositories;

public record CourseSearchParams(
    String nameSlug,
    String levelSlug,
    String categorySlug,
    String contributorId
) {
}
