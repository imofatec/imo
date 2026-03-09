package com.imo.backend.contexts.journey_tracking.use_cases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.guards.GetCourseByLessonIdGuard;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.guards.GetLessonByIdGuard;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.controllers.dtos.ProgressDTO;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.use_cases.commands.UpdateProgressCommand;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WatchLessonByIdUseCase {
  private final ProgressRepository progressRepository;
  private final CreateProgressUseCase createProgressUseCase;
  private final UpdateProgressByIdUseCase updateProgressByIdUseCase;
  private final GetCourseByLessonIdGuard getCourseByLessonIdGuard;
  private final GetLessonByIdGuard getLessonByIdGuard;

  public WatchLessonByIdUseCase(
      ProgressRepository progressRepository,
      CreateProgressUseCase createProgressUseCase,
      UpdateProgressByIdUseCase updateProgressByIdUseCase,
      GetCourseByLessonIdGuard getCourseByLessonIdGuard,
      GetLessonByIdGuard getLessonByIdGuard
  ) {
    this.progressRepository = progressRepository;
    this.createProgressUseCase = createProgressUseCase;
    this.updateProgressByIdUseCase = updateProgressByIdUseCase;
    this.getCourseByLessonIdGuard = getCourseByLessonIdGuard;
    this.getLessonByIdGuard = getLessonByIdGuard;
  }

  public ProgressDTO execute(String lessonId, String userId) {
    Course existingCourse = this.getCourseByLessonIdGuard.execute(lessonId);
    Progress currentProgress = this.progressRepository
        .findByUserIdAndCourseId(userId, existingCourse.getId())
        .orElse(null);

    if (currentProgress == null) {
      Lesson lesson = this.getLessonByIdGuard.execute(lessonId);
      List<String> lessonsIds = new ArrayList<>();
      lessonsIds.add(lesson.getId());
      return ProgressDTO.fromProgress(this.createProgressUseCase.execute(
          userId,
          existingCourse.getId(),
          lessonsIds
      ));
    }

    if (currentProgress.getStatus() == ProgressStatus.FINISHED) {
      return ProgressDTO.fromProgress(currentProgress);
    }

    currentProgress.watchLesson(lessonId, existingCourse.getLessonsCount());

    var updatedProgress = this.updateProgressByIdUseCase.execute(
        currentProgress.getId(),
        new UpdateProgressCommand(
            currentProgress.getProgressPeriod(),
            currentProgress.getStatus(),
            currentProgress.getLessonsWatched()
        )
    );

    return ProgressDTO.fromProgress(updatedProgress);
  }
}
