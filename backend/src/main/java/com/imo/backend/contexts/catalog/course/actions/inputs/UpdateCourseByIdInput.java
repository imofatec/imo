package com.imo.backend.contexts.catalog.course.actions.inputs;

import com.imo.backend.contexts.catalog.course.value_objects.Categories;

public record UpdateCourseByIdInput(
    String name,
    Categories category,
    String level,
    String description,
    Integer lessonsCount,
    String firstLessonYoutubeLink
) {
}
