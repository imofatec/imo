package com.imo.backend.contexts.journey_tracking.usecases.impl;

import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.policies.ProgressPolicy;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.usecases.CreateProgressUseCase;

import org.springframework.stereotype.Service;

@Service
public class CreateProgressUseCaseImpl implements CreateProgressUseCase {
  private final ProgressRepository progressRepository;

  private final ProgressPolicy startProgressPolicy;

  private final CourseRepository courseRepository;

  public CreateProgressUseCaseImpl(
      CourseRepository courseRepository,
      ProgressRepository progressRepository,
      ProgressPolicy startProgressPolicy) {
    this.progressRepository = progressRepository;
    this.startProgressPolicy = startProgressPolicy;
    this.courseRepository = courseRepository;
  }

  @Override
  public Progress execute(String userId, String courseId, String firstLessonId) {
    this.courseRepository.findByIdOrThrow(courseId);
    this.startProgressPolicy.execute(userId, courseId);

    Progress progress = Progress.assertStartWithFirstLesson(userId, courseId, firstLessonId);
    return this.progressRepository.save(progress);
  }
}
