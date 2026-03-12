package com.imo.backend.contexts.journey_tracking.actions.impl;

import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.actions.UpdateProgressByIdAction;
import com.imo.backend.contexts.journey_tracking.actions.helpers.ProgressUpdater;
import com.imo.backend.contexts.journey_tracking.actions.inputs.UpdateProgressInput;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateProgressByIdActionImpl implements UpdateProgressByIdAction {
  private final ProgressRepository progressRepository;

  public UpdateProgressByIdActionImpl(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  @Override
  public Progress execute(String id, UpdateProgressInput input) {
    var currentProgress = this.progressRepository.findById(id).orElse(null);

    if (currentProgress == null) {
      return null;
    }

    ProgressUpdater.apply(currentProgress, input);

    return this.progressRepository.save(currentProgress);
  }
}
