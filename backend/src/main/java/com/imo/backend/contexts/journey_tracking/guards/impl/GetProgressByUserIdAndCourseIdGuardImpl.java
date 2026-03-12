package com.imo.backend.contexts.journey_tracking.guards.impl;

import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.guards.GetProgressByUserIdAndCourseIdGuard;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class GetProgressByUserIdAndCourseIdGuardImpl
    implements GetProgressByUserIdAndCourseIdGuard {
  private final ProgressRepository progressRepository;

  public GetProgressByUserIdAndCourseIdGuardImpl(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  @Override
  public Progress execute(String userId, String courseId) throws NotFoundException {
    return this.progressRepository
        .findByUserIdAndCourseId(userId, courseId)
        .orElseThrow(() -> new NotFoundException("Progresso não encontrado"));
  }
}
