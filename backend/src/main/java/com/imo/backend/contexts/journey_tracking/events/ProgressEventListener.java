package com.imo.backend.contexts.journey_tracking.events;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.use_cases.UpdateProgressByIdUseCase;
import com.imo.backend.contexts.journey_tracking.use_cases.commands.UpdateProgressCommand;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressPeriod;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProgressEventListener {
  private final LessonRepository lessonRepository;

  private final ProgressRepository progressRepository;

  private final UpdateProgressByIdUseCase updateProgressByIdUseCase;

  public ProgressEventListener(
      LessonRepository lessonRepository,
      ProgressRepository progressRepository,
      UpdateProgressByIdUseCase updateProgressByIdUseCase
  ) {
    this.lessonRepository = lessonRepository;
    this.progressRepository = progressRepository;
    this.updateProgressByIdUseCase = updateProgressByIdUseCase;
  }

  @ApplicationModuleListener
  public void execute(ReevaluateProgressEvent event) {
    int page = 0;
    int size = 100;
    List<Progress> progressList;

    var courseId = event.courseId();
    do {
      progressList = this.progressRepository.findProgressByCourseId(courseId, page, size);

      progressList.forEach(progress -> {
        List<Lesson> existingLessons = this.lessonRepository.findAllByCourseId(courseId);
        Set<String> existingLessonsIds = existingLessons
            .stream()
            .map(Entity::getId)
            .collect(Collectors.toSet());

        boolean isFinished = progress.getStatus() == ProgressStatus.FINISHED;
        boolean hasMissingLessons = progress.getLessonsWatched().size() < existingLessons.size();

        if (isFinished && hasMissingLessons) {
          var input = new UpdateProgressCommand(
              new ProgressPeriod(progress.getProgressPeriod().startedAt(), null),
              ProgressStatus.IN_PROGRESS,
              null
          );
          this.updateProgressByIdUseCase.execute(progress.getId(), input);
          return;
        }

        boolean hasWatchedLessonDeleted = progress
            .getLessonsWatched()
            .stream()
            .anyMatch(watchedLesson -> !existingLessonsIds.contains(watchedLesson));


        if (!isFinished && hasWatchedLessonDeleted) {
          List<String> filteredWatchedLessons = progress
              .getLessonsWatched()
              .stream()
              .filter(existingLessonsIds::contains)
              .toList();

          var input = new UpdateProgressCommand(null, null, filteredWatchedLessons);
          this.updateProgressByIdUseCase.execute(progress.getId(), input);
          return;
        }

        if (!isFinished && progress.getLessonsWatched().size() == existingLessons.size()) {
          var input = new UpdateProgressCommand(
              new ProgressPeriod(progress.getProgressPeriod().startedAt(), LocalDateTime.now()),
              ProgressStatus.FINISHED,
              existingLessonsIds.stream().toList()
          );
          this.updateProgressByIdUseCase.execute(progress.getId(), input);
        }
      });

      page++;
    } while (progressList.size() == size);
  }
}
