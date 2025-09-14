package com.imo.backend.modules.progress.services.impl;

import com.imo.backend.exceptions.custom.ConflictException;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.course.guards.GetCourseByIdGuard;
import com.imo.backend.modules.progress.Progress;
import com.imo.backend.modules.progress.actions.CreateProgressAction;
import com.imo.backend.modules.progress.guards.GetProgressByUserIdAndCourseIdGuard;
import com.imo.backend.modules.progress.services.CreateProgressService;
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
