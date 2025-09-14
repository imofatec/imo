package com.imo.backend.modules.progress.actions.impl;

import com.imo.backend.modules.progress.Progress;
import com.imo.backend.modules.progress.actions.CreateProgressAction;
import com.imo.backend.modules.progress.repositories.ProgressRepository;
import com.imo.backend.modules.progress.value_objects.ProgressPeriod;
import com.imo.backend.modules.progress.value_objects.ProgressStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreateProgressActionImpl implements CreateProgressAction {
  private final ProgressRepository progressRepository;

  public CreateProgressActionImpl(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  @Override
  public Progress execute(String userId, String courseId, List<String> lessonsWatched) {
    Progress progress = new Progress(
        userId,
        courseId,
        lessonsWatched,
        new ProgressPeriod(LocalDateTime.now(), null),
        ProgressStatus.IN_PROGRESS
    );

    return this.progressRepository.save(progress);
  }
}
