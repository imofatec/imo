package com.imo.backend.contexts.catalog.lesson.actions.commands;

public record UpdateLessonCommand(
    String lessonId,
    String title,
    String description,
    String youtubeLink
) {
    
}
