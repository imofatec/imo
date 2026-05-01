package com.imo.backend.contexts.social.profile;

import java.time.LocalDateTime;

public record RecentCourseActivityDetails(
    String courseId,
    String courseName,
    LocalDateTime lastWatchedAt,
    String firstLessonYoutubeLink,
    int watchedLessonsCount,
    int totalLessonsCount,
    int completionPercentage) {}
