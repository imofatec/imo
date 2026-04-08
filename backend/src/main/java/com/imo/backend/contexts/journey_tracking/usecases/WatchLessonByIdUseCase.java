package com.imo.backend.contexts.journey_tracking.usecases;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class WatchLessonByIdUseCase {
  private final ProgressRepository progressRepository;

  private final CourseRepository courseRepository;

  public WatchLessonByIdUseCase(
      ProgressRepository progressRepository, CourseRepository courseRepository) {
    this.progressRepository = progressRepository;
    this.courseRepository = courseRepository;
  }

  public Progress execute(String lessonId, String userId) {
    Course course = this.courseRepository.findByLessonIdOrThrow(lessonId);

    Progress progress =
        this.progressRepository.findByUserIdAndCourseId(userId, course.getId()).orElse(null);

    if (progress == null) {
      progress = new Progress(userId, course.getId(), List.of(lessonId), course.getLessonsCount());
      return this.progressRepository.save(progress);
    }

    if (progress.getStatus() == ProgressStatus.FINISHED) {
      return progress;
    }

    progress.watchLesson(lessonId, course.getLessonsCount());
    return this.progressRepository.save(progress);
  }
}
