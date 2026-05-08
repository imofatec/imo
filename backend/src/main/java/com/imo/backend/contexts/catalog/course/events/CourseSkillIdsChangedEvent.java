package com.imo.backend.contexts.catalog.course.events;

import java.util.List;

public record CourseSkillIdsChangedEvent(
    String courseId, List<String> previousSkillIds, List<String> currentSkillIds) {}
