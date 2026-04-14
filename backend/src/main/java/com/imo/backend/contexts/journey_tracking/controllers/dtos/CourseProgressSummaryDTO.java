package com.imo.backend.contexts.journey_tracking.controllers.dtos;

public record CourseProgressSummaryDTO(
    int watchedLessonsCount, int totalLessonsCount, int completionPercentage) {
  public static CourseProgressSummaryDTO fromCounts(
      int watchedLessonsCount, int totalLessonsCount) {
    int completionPercentage =
        totalLessonsCount == 0 ? 0 : (watchedLessonsCount * 100) / totalLessonsCount;

    return new CourseProgressSummaryDTO(
        watchedLessonsCount, totalLessonsCount, completionPercentage);
  }
}
