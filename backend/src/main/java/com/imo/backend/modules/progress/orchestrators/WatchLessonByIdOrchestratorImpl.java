package com.imo.backend.modules.progress.orchestrators;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.guards.GetCourseByLessonIdGuard;
import com.imo.backend.modules.lesson.Lesson;
import com.imo.backend.modules.lesson.guards.GetLessonByIdGuard;
import com.imo.backend.modules.progress.Progress;
import com.imo.backend.modules.progress.actions.UpdateProgressByIdAction;
import com.imo.backend.modules.progress.actions.inputs.UpdateProgressInput;
import com.imo.backend.modules.progress.controllers.dtos.ProgressDTO;
import com.imo.backend.modules.progress.guards.GetProgressByUserIdAndCourseIdGuard;
import com.imo.backend.modules.progress.services.CreateProgressService;
import com.imo.backend.modules.progress.value_objects.ProgressStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WatchLessonByIdOrchestratorImpl implements WatchLessonByIdOrchestrator {
  private final GetProgressByUserIdAndCourseIdGuard getProgressByUserIdAndCourseIdGuard;

  private final CreateProgressService createProgressService;

  private final UpdateProgressByIdAction updateProgressByIdAction;

  private final GetCourseByLessonIdGuard getCourseByLessonIdGuard;

  private final GetLessonByIdGuard getLessonByIdGuard;

  public WatchLessonByIdOrchestratorImpl(
      GetProgressByUserIdAndCourseIdGuard getProgressByUserIdAndCourseIdGuard,
      CreateProgressService createProgressService,
      UpdateProgressByIdAction updateProgressByIdAction,
      GetCourseByLessonIdGuard getCourseByLessonIdGuard,
      GetLessonByIdGuard getLessonByIdGuard
  ) {
    this.getProgressByUserIdAndCourseIdGuard = getProgressByUserIdAndCourseIdGuard;
    this.createProgressService = createProgressService;
    this.updateProgressByIdAction = updateProgressByIdAction;
    this.getCourseByLessonIdGuard = getCourseByLessonIdGuard;
    this.getLessonByIdGuard = getLessonByIdGuard;
  }

  @Override
  public ProgressDTO execute(String lessonId, String userId) {
    Progress currentProgress = null;

    Course existingCourse = null;

    try {
      existingCourse = this.getCourseByLessonIdGuard.execute(lessonId);
      currentProgress = this.getProgressByUserIdAndCourseIdGuard.execute(
          userId,
          existingCourse.getId()
      );
    } catch (NotFoundException e) {
    }

    assert existingCourse != null;

    if (currentProgress == null) {
      Lesson lesson = this.getLessonByIdGuard.execute(lessonId);
      List<String> lessonsIds = new ArrayList<>();
      lessonsIds.add(lesson.getId());
      return ProgressDTO.fromProgress(this.createProgressService.execute(
          userId,
          existingCourse.getId(),
          lessonsIds
      ));
    }

    if (currentProgress.getStatus() == ProgressStatus.FINISHED) {
      return ProgressDTO.fromProgress(currentProgress);
    }

    currentProgress.watchLesson(lessonId, existingCourse.getLessonsCount());

    var updatedProgress = this.updateProgressByIdAction.execute(
        currentProgress.getId(), new UpdateProgressInput(
            currentProgress.getProgressPeriod(),
            currentProgress.getStatus(),
            currentProgress.getLessonsWatched()
        )
    );

    return ProgressDTO.fromProgress(updatedProgress);
  }
}
