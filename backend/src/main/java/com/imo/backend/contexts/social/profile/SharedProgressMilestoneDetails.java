package com.imo.backend.contexts.social.profile;

import java.time.LocalDateTime;

public record SharedProgressMilestoneDetails(
    String publicCode,
    String courseNameSnapshot,
    int watchedLessonsCountSnapshot,
    int totalLessonsCountSnapshot,
    int completionPercentageSnapshot,
    LocalDateTime updatedAt,
    LocalDateTime createdAt) {}
