package com.imo.backend.contexts.journey_tracking.progress.controllers.dtos;

import com.imo.backend.contexts.journey_tracking.progress.Progress;
import com.imo.backend.contexts.journey_tracking.progress.value_objects.ProgressPeriod;
import com.imo.backend.contexts.journey_tracking.progress.value_objects.ProgressStatus;
import java.util.List;

public record ProgressDTO(
    String id,
    String userId,
    String courseId,
    List<String> lessonsWatched,
    int lessonsCount,
    ProgressStatus status,
    ProgressPeriod progressPeriod) {
  public static ProgressDTO fromProgress(Progress progress) {
    return new ProgressDTO(
        progress.getId(),
        progress.getUserId(),
        progress.getCourseId(),
        progress.getLessonsWatched(),
        progress.getWatchedLessonsCount(),
        progress.getStatus(),
        progress.getProgressPeriod());
  }
}
