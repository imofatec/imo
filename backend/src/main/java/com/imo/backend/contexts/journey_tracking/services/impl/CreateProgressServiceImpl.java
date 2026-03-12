package com.imo.backend.contexts.journey_tracking.services.impl;

import com.imo.backend.contexts.catalog.course.guards.GetCourseByIdGuard;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.actions.CreateProgressAction;
import com.imo.backend.contexts.journey_tracking.guards.GetProgressByUserIdAndCourseIdGuard;
import com.imo.backend.contexts.journey_tracking.services.CreateProgressService;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreateProgressServiceImpl implements CreateProgressService {
  private final GetCourseByIdGuard getCourseByIdGuard;

  private final GetProgressByUserIdAndCourseIdGuard getProgressByUserIdAndCourseIdGuard;

  private final CreateProgressAction createProgressAction;

  public CreateProgressServiceImpl(
      GetCourseByIdGuard getCourseByIdGuard,
      GetProgressByUserIdAndCourseIdGuard getProgressByUserIdAndCourseIdGuard,
      CreateProgressAction createProgressAction
  ) {
    this.getCourseByIdGuard = getCourseByIdGuard;
    this.getProgressByUserIdAndCourseIdGuard = getProgressByUserIdAndCourseIdGuard;
    this.createProgressAction = createProgressAction;
  }

  @Override
  public Progress execute(String userId, String courseId, List<String> lessonsWatched) {
    this.getCourseByIdGuard.execute(courseId);

    try {
      this.getProgressByUserIdAndCourseIdGuard.execute(userId, courseId);
      throw new ConflictException("Progresso ja foi inicado");
    } catch (NotFoundException ignored) {
      return this.createProgressAction.execute(userId, courseId, lessonsWatched);
    }
  }
}
