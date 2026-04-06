package com.imo.backend.contexts.journey_tracking.services.impl;

import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.actions.CreateProgressAction;
import com.imo.backend.contexts.journey_tracking.guards.GetProgressByUserIdAndCourseIdGuard;
import com.imo.backend.contexts.journey_tracking.services.CreateProgressService;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CreateProgressServiceImpl implements CreateProgressService {
  private final GetProgressByUserIdAndCourseIdGuard getProgressByUserIdAndCourseIdGuard;

  private final CreateProgressAction createProgressAction;

  private final CourseRepository courseRepository;

  public CreateProgressServiceImpl(
      GetProgressByUserIdAndCourseIdGuard getProgressByUserIdAndCourseIdGuard,
      CreateProgressAction createProgressAction,
      CourseRepository courseRepository) {
    this.getProgressByUserIdAndCourseIdGuard = getProgressByUserIdAndCourseIdGuard;
    this.createProgressAction = createProgressAction;
    this.courseRepository = courseRepository;
  }

  @Override
  public Progress execute(String userId, String courseId, List<String> lessonsWatched) {
    this.courseRepository.findByIdOrThrow(courseId);

    try {
      this.getProgressByUserIdAndCourseIdGuard.execute(userId, courseId);
      throw new ConflictException("Progresso ja foi inicado");
    } catch (NotFoundException ignored) {
      return this.createProgressAction.execute(userId, courseId, lessonsWatched);
    }
  }
}
