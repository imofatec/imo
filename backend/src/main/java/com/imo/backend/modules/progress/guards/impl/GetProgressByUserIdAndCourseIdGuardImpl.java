package com.imo.backend.modules.progress.guards.impl;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.progress.Progress;
import com.imo.backend.modules.progress.guards.GetProgressByUserIdAndCourseIdGuard;
import com.imo.backend.modules.progress.repositories.ProgressRepository;
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
