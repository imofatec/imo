package com.imo.backend.contexts.journey_tracking.actions.impl;

import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.actions.CreateProgressAction;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressPeriod;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreateProgressActionImpl implements CreateProgressAction {
  private final ProgressRepository progressRepository;

  private final CourseRepository courseRepository;

  public CreateProgressActionImpl(
      ProgressRepository progressRepository,
      CourseRepository courseRepository
  ) {
    this.progressRepository = progressRepository;
    this.courseRepository = courseRepository;
  }

  @Override
  public Progress execute(String userId, String courseId, List<String> lessonsWatched) {


    var course = this.courseRepository.findByIdOrThrow(courseId);

    boolean isFinished = course.getLessonsCount() == lessonsWatched.size();

    ProgressStatus status = isFinished ? ProgressStatus.FINISHED : ProgressStatus.IN_PROGRESS;

    ProgressPeriod progressPeriod = isFinished ? new ProgressPeriod(
        LocalDateTime.now(),
        LocalDateTime.now()
    ) : new ProgressPeriod(LocalDateTime.now(), null);

    Progress progress = new Progress(userId, courseId, lessonsWatched, progressPeriod, status);

    return this.progressRepository.save(progress);
  }
}
