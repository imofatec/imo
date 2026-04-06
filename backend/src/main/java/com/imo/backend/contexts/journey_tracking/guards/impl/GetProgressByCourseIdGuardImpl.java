package com.imo.backend.contexts.journey_tracking.guards.impl;

import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.guards.GetProgressByCourseIdGuard;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class GetProgressByCourseIdGuardImpl implements GetProgressByCourseIdGuard {
  private final ProgressRepository progressRepository;

  public GetProgressByCourseIdGuardImpl(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  @Override
  public List<Progress> execute(String courseId, int page, int size) {
    return this.progressRepository.findProgressByCourseId(courseId, page, size);
  }
}
