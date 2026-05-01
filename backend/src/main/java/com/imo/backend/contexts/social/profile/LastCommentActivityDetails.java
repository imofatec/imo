package com.imo.backend.contexts.social.profile;

import java.time.LocalDateTime;

public record LastCommentActivityDetails(
    String id,
    String content,
    LocalDateTime createdAt,
    String lessonId,
    String lessonYoutubeLink,
    String lessonTitle,
    String courseId,
    String courseName) {}
