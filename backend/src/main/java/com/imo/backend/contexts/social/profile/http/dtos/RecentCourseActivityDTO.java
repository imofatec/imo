package com.imo.backend.contexts.social.profile.http.dtos;

public record RecentCourseActivityDTO(
    String courseId,
    String courseName,
    String firstLessonYoutubeLink,
    int watchedLessonsCount,
    int totalLessonsCount) {}
