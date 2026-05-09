package com.imo.backend.contexts.catalog.lesson.events;

public record LessonsListUpdatedEvent(String courseId, int newCount) {}
