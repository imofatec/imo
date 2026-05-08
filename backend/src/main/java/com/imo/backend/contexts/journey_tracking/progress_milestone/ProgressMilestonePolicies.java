package com.imo.backend.contexts.journey_tracking.progress_milestone;

import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.progress.ProgressDetails;
import com.imo.backend.contexts.journey_tracking.progress.ProgressStatus;
import com.imo.backend.contexts.journey_tracking.progress.repositories.ProgressRepository;
import org.springframework.stereotype.Service;

@Service
public class ProgressMilestonePolicies {
  private final ProgressRepository progressRepository;

  public ProgressMilestonePolicies(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  public ProgressDetails assertCourseIsFinished(String userId, String courseId) {
    ProgressDetails progressDetails =
        this.progressRepository
            .findDetailsByUserIdAndCourseId(userId, courseId)
            .orElseThrow(() -> new NotFoundException("Progresso não encontrado."));

    if (progressDetails.progress().getStatus() != ProgressStatus.FINISHED) {
      throw new ForbiddenException("Finalize o curso para compartilhar este marco de progresso.");
    }

    return progressDetails;
  }
}
