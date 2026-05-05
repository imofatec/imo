package com.imo.backend.contexts.journey_tracking.progress.controllers.dtos;

import com.imo.backend.contexts.journey_tracking.progress.Progress;

public record CourseProgressSummaryDTO(
    int watchedLessonsCount, int totalLessonsCount, int completionPercentage) {
  public static CourseProgressSummaryDTO fromProgress(Progress progress, int totalLessonsCount) {
    return new CourseProgressSummaryDTO(
        progress.getWatchedLessonsCount(),
        totalLessonsCount,
        progress.calculateCompletionPercentage(totalLessonsCount));
  }
}
