package com.imo.backend.contexts.catalog.lesson.events;

public record FirstLessonInCourseUpdatedEvent(String courseId, String newFirstYoutubeLink) {}
