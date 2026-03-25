package com.imo.backend.contexts.certification;

import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import org.springframework.stereotype.Service;

@Service
public class CertificatePolicies {
  private final ProgressRepository progressRepository;

  public CertificatePolicies(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  public Progress assertCourseIsFinished(String userId, String courseId) {
    Progress progress = this.progressRepository
        .findByUserIdAndCourseId(userId, courseId)
        .orElseThrow(() -> new NotFoundException("Progresso não encontrado."));

    if (progress.getStatus() != ProgressStatus.FINISHED) {
      throw new ForbiddenException("Finalize o curso para emitir o certificado.");
    }

    return progress;
  }
}
