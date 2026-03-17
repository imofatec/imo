package com.imo.backend.contexts.journey_tracking.policies;

import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressPeriod;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.List;

@Component
public class ProgressPolicy {
  private final ProgressRepository progressRepository;

  public ProgressPolicy(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  public Progress startProgress(
      String userId,
      String courseId,
      List<String> lessonsWatched,
      int totalLessons) {
    Optional<Progress> foundProgress = this.progressRepository.findByUserIdAndCourseId(userId, courseId);

    if (foundProgress.isPresent()) {
      throw new ConflictException("Progresso ja foi inicado");
    }

    boolean isFinished = totalLessons == lessonsWatched.size();
    LocalDateTime now = LocalDateTime.now();

    ProgressPeriod progressPeriod = isFinished
        ? new ProgressPeriod(now, now)
        : new ProgressPeriod(now, null);

    ProgressStatus status = isFinished
        ? ProgressStatus.FINISHED
        : ProgressStatus.IN_PROGRESS;

    return new Progress(userId, courseId, lessonsWatched, progressPeriod, status);

  }
}
