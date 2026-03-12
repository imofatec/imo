package com.imo.backend.contexts.certification.actions.impl;

import com.imo.backend.contexts.certification.Certificate;
import com.imo.backend.contexts.certification.actions.CreateCertificateAction;
import com.imo.backend.contexts.certification.repositories.CertificateRepository;
import com.imo.backend.contexts.certification.values_objects.CertificatePeriod;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateCertificateActionImpl implements CreateCertificateAction {
  private final ProgressRepository progressRepository;

  private final CertificateRepository certificateRepository;

  public CreateCertificateActionImpl(
      ProgressRepository progressRepository,
      CertificateRepository certificateRepository
  ) {
    this.progressRepository = progressRepository;
    this.certificateRepository = certificateRepository;
  }

  @Override
  public Certificate execute(String userId, String courseId) {
    Progress progress = progressRepository
        .findByUserIdAndCourseId(userId, courseId)
        .orElseThrow(() -> new NotFoundException("Progresso não encontrado"));

    if (progress.getStatus() != ProgressStatus.FINISHED) {
      throw new ForbiddenException("Finalize o curso para emitir o certificado");
    }

    return this.certificateRepository.save(new Certificate(
        userId,
        courseId,
        new CertificatePeriod(
            progress.getProgressPeriod().startedAt(),
            progress.getProgressPeriod().finishedAt()
        ),
        LocalDateTime.now()
    ));
  }
}
