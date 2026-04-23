package com.imo.backend.contexts.journey_tracking.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.events.CourseFinishedEvent;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class WatchLessonByIdUseCase {
  private final ProgressRepository progressRepository;
  private final CourseRepository courseRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  public WatchLessonByIdUseCase(
      ProgressRepository progressRepository,
      CourseRepository courseRepository,
      ApplicationEventPublisher applicationEventPublisher) {
    this.progressRepository = progressRepository;
    this.courseRepository = courseRepository;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  public Progress execute(String lessonId, String userId) {
    Course course = this.courseRepository.findByLessonIdOrThrow(lessonId);
    course.assertCanProgress();

    Progress progress =
        this.progressRepository.findByUserIdAndCourseId(userId, course.getId()).orElse(null);

    if (progress == null) {
      Progress newProgress =
          new Progress(userId, course.getId(), List.of(lessonId), course.getLessonsCount());

      Progress savedProgress = this.progressRepository.save(newProgress);
      this.publishCourseFinishedEventIfNeeded(savedProgress);

      return savedProgress;
    }

    if (progress.getStatus() == ProgressStatus.FINISHED) {
      return progress;
    }

    progress.watchLesson(lessonId, course.getLessonsCount());

    Progress savedProgress = this.progressRepository.save(progress);
    this.publishCourseFinishedEventIfNeeded(savedProgress);

    return savedProgress;
  }

  private void publishCourseFinishedEventIfNeeded(Progress progress) {
    if (progress.getStatus() != ProgressStatus.FINISHED) {
      return;
    }

    this.applicationEventPublisher.publishEvent(
        new CourseFinishedEvent(progress.getUserId(), progress.getCourseId()));
  }
}
