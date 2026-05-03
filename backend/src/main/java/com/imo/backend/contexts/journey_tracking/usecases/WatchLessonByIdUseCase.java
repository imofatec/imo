package com.imo.backend.contexts.journey_tracking.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.events.CourseFinishedEvent;
import com.imo.backend.contexts.journey_tracking.events.LessonWatchedEvent;
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
      progress = new Progress(userId, course.getId(), List.of(lessonId), course.getLessonsCount());

      Progress newProgress = this.progressRepository.save(progress);
      this.emitEvents(newProgress.getStatus(), lessonId, course.getId(), userId);

      return newProgress;
    }

    boolean wasAlreadyFinished = progress.getStatus() == ProgressStatus.FINISHED;

    if (wasAlreadyFinished) {
      return progress;
    }

    progress.watchLesson(lessonId, course.getLessonsCount());
    Progress updatedProgress = this.progressRepository.save(progress);
    this.emitEvents(updatedProgress.getStatus(), lessonId, course.getId(), userId);
    return updatedProgress;
  }

  private void emitEvents(ProgressStatus status, String lessonId, String courseId, String userId) {
    this.applicationEventPublisher.publishEvent(new LessonWatchedEvent(lessonId, userId));

    if (status == ProgressStatus.FINISHED) {
      this.applicationEventPublisher.publishEvent(new CourseFinishedEvent(courseId, userId));
    }
  }
}
