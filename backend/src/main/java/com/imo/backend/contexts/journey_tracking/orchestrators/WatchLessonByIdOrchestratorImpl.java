package com.imo.backend.contexts.journey_tracking.orchestrators;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.commands.UpdateProgressCommand;
import com.imo.backend.contexts.journey_tracking.controllers.dtos.ProgressDTO;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.services.CreateProgressService;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import org.springframework.stereotype.Service;

@Service
public class WatchLessonByIdOrchestratorImpl implements WatchLessonByIdOrchestrator {
  private final ProgressRepository progressRepository;

  private final CreateProgressService createProgressService;

  private final CourseRepository courseRepository;

  public WatchLessonByIdOrchestratorImpl(
      ProgressRepository progressRepository,
      CreateProgressService createProgressService,
      CourseRepository courseRepository) {
    this.progressRepository = progressRepository;
    this.createProgressService = createProgressService;
    this.courseRepository = courseRepository;
  }

  @Override
  public ProgressDTO execute(String lessonId, String userId) {
    Progress currentProgress = null;

    Course existingCourse = null;

    existingCourse = this.courseRepository.findByLessonIdOrThrow(lessonId);

    try {
      currentProgress = this.progressRepository.findByUserIdAndCourseIdOrThrow(userId, existingCourse.getId());
    } catch (NotFoundException ignored) {
    }

    assert existingCourse != null;

    if (currentProgress == null) {
      return ProgressDTO.fromProgress(this.createProgressService.execute(
          userId,
          existingCourse.getId(),
          lessonId));
    }

    if (currentProgress.getStatus() == ProgressStatus.FINISHED) {
      return ProgressDTO.fromProgress(currentProgress);
    }

    currentProgress.watchLesson(lessonId, existingCourse.getLessonsCount());

    currentProgress.progressUpdater(
        new UpdateProgressCommand(
            currentProgress.getProgressPeriod(),
            currentProgress.getStatus(),
            currentProgress.getLessonsWatched()));
    var updatedProgress = this.progressRepository.save(currentProgress);

    return ProgressDTO.fromProgress(updatedProgress);
  }
}
