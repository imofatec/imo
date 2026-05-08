package com.imo.backend.contexts.catalog.course.events;

import java.util.List;

public record CourseCreatedEvent(String courseId, List<String> skillIds) {}
