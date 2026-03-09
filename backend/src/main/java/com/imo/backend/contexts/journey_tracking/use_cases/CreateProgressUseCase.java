package com.imo.backend.contexts.journey_tracking.use_cases;

import com.imo.backend.contexts.catalog.course.guards.GetCourseByIdGuard;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressPeriod;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreateProgressUseCase {
  private final GetCourseByIdGuard getCourseByIdGuard;
  private final ProgressRepository progressRepository;

  public CreateProgressUseCase(
      GetCourseByIdGuard getCourseByIdGuard,
      ProgressRepository progressRepository
  ) {
    this.getCourseByIdGuard = getCourseByIdGuard;
    this.progressRepository = progressRepository;
  }

  public Progress execute(String userId, String courseId, List<String> lessonsWatched) {
    var course = this.getCourseByIdGuard.execute(courseId);

    if (this.progressRepository.findByUserIdAndCourseId(userId, courseId).isPresent()) {
      throw new ConflictException("Progresso ja foi inicado");
    }

    boolean isFinished = course.getLessonsCount() == lessonsWatched.size();
    ProgressStatus status = isFinished ? ProgressStatus.FINISHED : ProgressStatus.IN_PROGRESS;

    ProgressPeriod progressPeriod = isFinished
        ? new ProgressPeriod(LocalDateTime.now(), LocalDateTime.now())
        : new ProgressPeriod(LocalDateTime.now(), null);

    Progress progress = new Progress(userId, courseId, lessonsWatched, progressPeriod, status);

    return this.progressRepository.save(progress);
  }
}
