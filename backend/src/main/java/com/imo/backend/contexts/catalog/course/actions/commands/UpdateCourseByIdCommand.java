package com.imo.backend.contexts.catalog.course.actions.commands;

import com.imo.backend.contexts.catalog.course.value_objects.Categories;

public record UpdateCourseByIdCommand(
    String id,
    String name,
    Categories category,
    String level,
    String description,
    Integer lessonsCount,
    String firstLessonYoutubeLink) {}
