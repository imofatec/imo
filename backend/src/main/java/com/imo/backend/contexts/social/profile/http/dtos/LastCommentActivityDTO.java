package com.imo.backend.contexts.social.profile.http.dtos;

import com.imo.backend.contexts.common.HttpDateTimeFormatter;
import com.imo.backend.contexts.social.profile.LastCommentActivityDetails;

public record LastCommentActivityDTO(
    String content,
    String createdAt,
    String lessonTitle,
    String lessonYoutubeLink,
    String courseId,
    String courseName) {
  public static LastCommentActivityDTO fromDetails(LastCommentActivityDetails comment) {
    return new LastCommentActivityDTO(
        comment.content(),
        HttpDateTimeFormatter.toDateTime(comment.createdAt()),
        comment.lessonTitle(),
        comment.lessonYoutubeLink(),
        comment.courseId(),
        comment.courseName());
  }
}
