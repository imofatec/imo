package com.imo.backend.contexts.journey_tracking.orchestrators;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.actions.UpdateProgressByIdAction;
import com.imo.backend.contexts.journey_tracking.actions.inputs.UpdateProgressInput;
import com.imo.backend.contexts.journey_tracking.controllers.dtos.ProgressDTO;
import com.imo.backend.contexts.journey_tracking.guards.GetProgressByUserIdAndCourseIdGuard;
import com.imo.backend.contexts.journey_tracking.services.CreateProgressService;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WatchLessonByIdOrchestratorImpl implements WatchLessonByIdOrchestrator {
  private final GetProgressByUserIdAndCourseIdGuard getProgressByUserIdAndCourseIdGuard;

  private final CreateProgressService createProgressService;

  private final UpdateProgressByIdAction updateProgressByIdAction;

  private final CourseRepository courseRepository;

  private final LessonRepository lessonRepository;

  public WatchLessonByIdOrchestratorImpl(
      GetProgressByUserIdAndCourseIdGuard getProgressByUserIdAndCourseIdGuard,
      CreateProgressService createProgressService,
      UpdateProgressByIdAction updateProgressByIdAction,
      CourseRepository courseRepository,
      LessonRepository lessonRepository) {
    this.getProgressByUserIdAndCourseIdGuard = getProgressByUserIdAndCourseIdGuard;
    this.createProgressService = createProgressService;
    this.updateProgressByIdAction = updateProgressByIdAction;
    this.courseRepository = courseRepository;
    this.lessonRepository = lessonRepository;
  }

  @Override
  public ProgressDTO execute(String lessonId, String userId) {
    Progress currentProgress = null;

    Course existingCourse = null;

    existingCourse = this.courseRepository.findByLessonIdOrThrow(lessonId);

    try {

      currentProgress =
          this.getProgressByUserIdAndCourseIdGuard.execute(userId, existingCourse.getId());
    } catch (NotFoundException e) {
    }

    assert existingCourse != null;

    if (currentProgress == null) {
      Lesson lesson = this.lessonRepository.findByIdOrThrow(lessonId);
      List<String> lessonsIds = new ArrayList<>();
      lessonsIds.add(lesson.getId());
      return ProgressDTO.fromProgress(
          this.createProgressService.execute(userId, existingCourse.getId(), lessonsIds));
    }

    if (currentProgress.getStatus() == ProgressStatus.FINISHED) {
      return ProgressDTO.fromProgress(currentProgress);
    }

    currentProgress.watchLesson(lessonId, existingCourse.getLessonsCount());

    var updatedProgress =
        this.updateProgressByIdAction.execute(
            currentProgress.getId(),
            new UpdateProgressInput(
                currentProgress.getProgressPeriod(),
                currentProgress.getStatus(),
                currentProgress.getLessonsWatched()));

    return ProgressDTO.fromProgress(updatedProgress);
  }
}
