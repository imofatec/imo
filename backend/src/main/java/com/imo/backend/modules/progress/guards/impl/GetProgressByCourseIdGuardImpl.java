package com.imo.backend.modules.progress.guards.impl;

import com.imo.backend.modules.progress.Progress;
import com.imo.backend.modules.progress.guards.GetProgressByCourseIdGuard;
import com.imo.backend.modules.progress.repositories.ProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;

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
