package com.imo.backend.contexts.journey_tracking.actions.impl;

import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.actions.CreateProgressAction;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateProgressActionImpl implements CreateProgressAction {
  private final ProgressRepository progressRepository;

  private final CourseRepository courseRepository;

  public CreateProgressActionImpl(
      ProgressRepository progressRepository,
      CourseRepository courseRepository) {
    this.progressRepository = progressRepository;
    this.courseRepository = courseRepository;
  }

  @Override
  public Progress execute(String userId, String courseId, List<String> lessonsWatched) {
    var course = this.courseRepository.findByIdOrThrow(courseId);
    Progress progress = Progress.create(userId, courseId, lessonsWatched, course.getLessonsCount());

    return this.progressRepository.save(progress);
  }
}
