package com.imo.backend.contexts.journey_tracking.progress_milestone.http.dtos;

import com.imo.backend.contexts.common.HttpDateTimeFormatter;
import com.imo.backend.contexts.journey_tracking.progress_milestone.ProgressMilestone;

public record ProgressMilestoneDTO(
    String id,
    String publicCode,
    String authorName,
    String courseName,
    int watchedLessonsCount,
    int totalLessonsCount,
    int completionPercentage,
    String courseStartedAt,
    String courseFinishedAt,
    String generatedAt,
    String shareUrl,
    String imageUrl) {
  public static ProgressMilestoneDTO fromProgressMilestone(
      ProgressMilestone progressMilestone, String baseUrl) {
    return new ProgressMilestoneDTO(
        progressMilestone.getId(),
        progressMilestone.getPublicCode(),
        progressMilestone.getAuthorNameSnapshot(),
        progressMilestone.getCourseNameSnapshot(),
        progressMilestone.getWatchedLessonsCountSnapshot(),
        progressMilestone.getTotalLessonsCountSnapshot(),
        progressMilestone.getCompletionPercentageSnapshot(),
        HttpDateTimeFormatter.toDate(progressMilestone.getCourseStartedAtSnapshot()),
        HttpDateTimeFormatter.toDate(progressMilestone.getCourseFinishedAtSnapshot()),
        HttpDateTimeFormatter.toDateTime(progressMilestone.getGeneratedAt()),
        buildShareUrl(baseUrl, progressMilestone.getPublicCode()),
        buildImageUrl(baseUrl, progressMilestone.getPublicCode()));
  }

  public static String buildShareUrl(String baseUrl, String publicCode) {
    return baseUrl + "/m/" + publicCode;
  }

  public static String buildImageUrl(String baseUrl, String publicCode) {
    return baseUrl + "/api/progress/milestones/public/" + publicCode + "/image.png";
  }
}
