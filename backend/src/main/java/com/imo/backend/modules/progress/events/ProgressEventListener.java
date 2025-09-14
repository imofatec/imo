package com.imo.backend.modules.progress.events;

import com.imo.backend.modules.base.Entity;
import com.imo.backend.modules.lesson.Lesson;
import com.imo.backend.modules.lesson.repositories.LessonRepository;
import com.imo.backend.modules.progress.Progress;
import com.imo.backend.modules.progress.actions.UpdateProgressByIdAction;
import com.imo.backend.modules.progress.actions.inputs.UpdateProgressInput;
import com.imo.backend.modules.progress.guards.GetProgressByCourseIdGuard;
import com.imo.backend.modules.progress.value_objects.ProgressPeriod;
import com.imo.backend.modules.progress.value_objects.ProgressStatus;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class ProgressEventListener {
  private final LessonRepository lessonRepository;

  private final GetProgressByCourseIdGuard getProgressByCourseIdGuard;

  private final UpdateProgressByIdAction updateProgressByIdAction;

  public ProgressEventListener(
      LessonRepository lessonRepository,
      GetProgressByCourseIdGuard getProgressByCourseIdGuard,
      UpdateProgressByIdAction updateProgressByIdAction
  ) {
    this.lessonRepository = lessonRepository;
    this.getProgressByCourseIdGuard = getProgressByCourseIdGuard;
    this.updateProgressByIdAction = updateProgressByIdAction;
  }

  @ApplicationModuleListener
  public void execute(ReevaluateProgressEvent event) {
    int page = 0;
    int size = 100;
    List<Progress> progressList;

    var courseId = event.courseId();
    do {
      progressList = this.getProgressByCourseIdGuard.execute(courseId, page, size);

      progressList.forEach(progress -> {
        List<Lesson> existingLessons = this.lessonRepository.findAllByCourseId(courseId);
        Set<String> existingLessonsIds = existingLessons
            .stream()
            .map(Entity::getId)
            .collect(Collectors.toSet());

        boolean isFinished = progress.getStatus() == ProgressStatus.FINISHED;
        boolean hasMissingLessons = progress.getLessonsWatched().size() < existingLessons.size();

        if (isFinished && hasMissingLessons) {
          var input = new UpdateProgressInput(
              new ProgressPeriod(progress.getProgressPeriod().startedAt(), null),
              ProgressStatus.IN_PROGRESS,
              null
          );
          this.updateProgressByIdAction.execute(progress.getId(), input);
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

          var input = new UpdateProgressInput(null, null, filteredWatchedLessons);
          this.updateProgressByIdAction.execute(progress.getId(), input);
          return;
        }

        if (!isFinished && progress.getLessonsWatched().size() == existingLessons.size()) {
          var input = new UpdateProgressInput(
              new ProgressPeriod(progress.getProgressPeriod().startedAt(), LocalDateTime.now()),
              ProgressStatus.FINISHED,
              existingLessonsIds.stream().toList()
          );
          this.updateProgressByIdAction.execute(progress.getId(), input);
        }
      });

      page++;
    } while (progressList.size() == size);
  }
}
