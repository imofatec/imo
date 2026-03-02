package com.imo.backend.modules.course.actions.inputs;

import com.imo.backend.modules.course.value_objects.Categories;

public record UpdateCourseByIdInput(
    String name,
    Categories category,
    String level,
    String description,
    Integer lessonsCount,
    String firstLessonYoutubeLink
) {
}
