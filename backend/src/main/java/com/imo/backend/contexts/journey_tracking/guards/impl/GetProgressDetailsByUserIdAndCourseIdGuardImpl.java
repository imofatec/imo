package com.imo.backend.contexts.journey_tracking.guards.impl;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.ProgressDetails;
import com.imo.backend.contexts.journey_tracking.guards.GetProgressDetailsByUserIdAndCourseIdGuard;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import org.springframework.stereotype.Service;

@Service
public class GetProgressDetailsByUserIdAndCourseIdGuardImpl
    implements GetProgressDetailsByUserIdAndCourseIdGuard {
  private final ProgressRepository progressRepository;

  public GetProgressDetailsByUserIdAndCourseIdGuardImpl(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  @Override
  public ProgressDetails execute(String userId, String courseId) {
    return this.progressRepository
        .findDetailsByUserIdAndCourseId(userId, courseId)
        .orElseThrow(() -> new NotFoundException("Progresso não encontrado"));
  }
}
