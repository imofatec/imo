package com.imo.backend.contexts.social.profile.http.dtos;

import java.time.LocalDateTime;

public record LastCommentActivityDTO(
    String content,
    LocalDateTime createdAt,
    String lessonTitle,
    String lessonYoutubeLink,
    String courseId,
    String courseName) {}
