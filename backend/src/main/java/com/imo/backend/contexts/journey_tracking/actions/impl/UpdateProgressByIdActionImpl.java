package com.imo.backend.contexts.journey_tracking.actions.impl;

import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.actions.UpdateProgressByIdAction;
import com.imo.backend.contexts.journey_tracking.commands.UpdateProgressCommand;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateProgressByIdActionImpl implements UpdateProgressByIdAction {
  private final ProgressRepository progressRepository;

  public UpdateProgressByIdActionImpl(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  @Override
  public Progress execute(String id, UpdateProgressCommand cmd) {
    var currentProgress = this.progressRepository.findById(id).orElse(null);

    if (currentProgress == null) {
      return null;
    }

    currentProgress.progressUpdater(cmd);

    return this.progressRepository.save(currentProgress);
  }
}
