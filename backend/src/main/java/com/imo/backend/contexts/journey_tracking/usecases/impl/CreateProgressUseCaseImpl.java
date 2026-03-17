package com.imo.backend.contexts.journey_tracking.usecases.impl;

import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.journey_tracking.policies.ProgressPolicy;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.usecases.CreateProgressUseCase;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class CreateProgressUseCaseImpl implements CreateProgressUseCase {
  private final ProgressRepository progressRepository;

  private final ProgressPolicy policy;

  private final CourseRepository courseRepository;

  public CreateProgressUseCaseImpl(
      CourseRepository courseRepository,
      ProgressRepository progressRepository,
      ProgressPolicy policy) {
    this.courseRepository = courseRepository;
    this.progressRepository = progressRepository;
    this.policy = policy;
  }

  @Override
  public Progress execute(String userId, String courseId, String firstLessonId) {
    Course course = this.courseRepository.findByIdOrThrow(courseId);
    Progress progress = this.policy.startProgress(
        userId,
        courseId,
        List.of(firstLessonId),
        course.getLessonsCount());

    return this.progressRepository.save(progress);
  }
}
