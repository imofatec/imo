package com.imo.backend.contexts.catalog.course.commands;

import com.imo.backend.contexts.catalog.course.Categories;
import com.imo.backend.contexts.catalog.lesson.commands.CreateLessonCommand;
import java.util.List;

public record CreateCourseCommand(
    String name,
    Categories category,
    String level,
    String description,
    List<CreateLessonCommand> lessons,
    String contributorId,
    List<String> skillIds) {}
