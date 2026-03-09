package com.imo.backend.contexts.journey_tracking.use_cases;

import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.use_cases.commands.UpdateProgressCommand;
import com.imo.backend.contexts.journey_tracking.use_cases.helpers.ProgressUpdater;
import org.springframework.stereotype.Service;

@Service
public class UpdateProgressByIdUseCase {
  private final ProgressRepository progressRepository;

  public UpdateProgressByIdUseCase(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  public Progress execute(String id, UpdateProgressCommand command) {
    var currentProgress = this.progressRepository.findByIdOrThrow(id);
    ProgressUpdater.apply(currentProgress, command);
    return this.progressRepository.save(currentProgress);
  }
}
