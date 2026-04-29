package com.imo.backend.contexts.catalog.course.events;

import java.util.List;

public record CourseStatusToggledEvent(String courseId, List<String> skillIds, boolean isActive) {}
