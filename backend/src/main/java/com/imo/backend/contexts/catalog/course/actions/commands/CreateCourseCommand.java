package com.imo.backend.contexts.catalog.course.actions.commands;

import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.lesson.actions.commands.CreateLessonCommand;
import java.util.List;

public record CreateCourseCommand(
    String name,
    Categories category,
    String level,
    String description,
    List<CreateLessonCommand> lessons,
    String contributorId) {}
