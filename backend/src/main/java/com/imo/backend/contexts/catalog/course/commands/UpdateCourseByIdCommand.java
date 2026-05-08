package com.imo.backend.contexts.catalog.course.commands;

import com.imo.backend.contexts.catalog.course.Categories;
import java.util.List;

public record UpdateCourseByIdCommand(
    String id,
    String name,
    Categories category,
    String level,
    String description,
    Integer lessonsCount,
    String firstLessonYoutubeLink,
    List<String> skillIds) {}
