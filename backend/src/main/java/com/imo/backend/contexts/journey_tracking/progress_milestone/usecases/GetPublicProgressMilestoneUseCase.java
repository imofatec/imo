package com.imo.backend.contexts.journey_tracking.progress_milestone.usecases;

import com.imo.backend.contexts.journey_tracking.progress_milestone.ProgressMilestone;
import com.imo.backend.contexts.journey_tracking.progress_milestone.repositories.ProgressMilestoneRepository;
import org.springframework.stereotype.Service;

@Service
public class GetPublicProgressMilestoneUseCase {
  private final ProgressMilestoneRepository progressMilestoneRepository;

  public GetPublicProgressMilestoneUseCase(
      ProgressMilestoneRepository progressMilestoneRepository) {
    this.progressMilestoneRepository = progressMilestoneRepository;
  }

  public ProgressMilestone execute(String publicCode) {
    return this.progressMilestoneRepository.findByPublicCodeOrThrow(publicCode);
  }
}
