package com.imo.backend.contexts.journey_tracking.services.impl;

import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.policies.ProgressPolicy;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.services.CreateProgressService;
import org.springframework.stereotype.Service;

@Service
public class CreateProgressServiceImpl implements CreateProgressService {
  private final ProgressRepository progressRepository;

  private final ProgressPolicy startProgressPolicy;

  private final CourseRepository courseRepository;

  public CreateProgressServiceImpl(
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
