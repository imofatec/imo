package com.imo.backend.contexts.journey_tracking.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.commands.UpdateProgressCommand;
import com.imo.backend.contexts.journey_tracking.controllers.dtos.ProgressDTO;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.policies.ProgressPolicy;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchLessonByIdUseCase {
  private final ProgressRepository progressRepository;

  private final CourseRepository courseRepository;

  private final ProgressPolicy policy;

  public WatchLessonByIdUseCase(
      ProgressRepository progressRepository,
      CourseRepository courseRepository,
      ProgressPolicy policy) {
    this.progressRepository = progressRepository;
    this.courseRepository = courseRepository;
    this.policy = policy;
  }

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
      String courseId = existingCourse.getId();
      Course course = this.courseRepository.findByIdOrThrow(courseId);
      Progress progress = this.policy.startProgress(
          userId,
          courseId,
          List.of(lessonId),
          course.getLessonsCount());

      Progress newProgress = this.progressRepository.save(progress);

      return ProgressDTO.fromProgress(newProgress);
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
