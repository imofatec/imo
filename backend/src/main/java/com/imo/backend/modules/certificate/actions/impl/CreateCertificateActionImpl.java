package com.imo.backend.modules.certificate.actions.impl;

import com.imo.backend.exceptions.custom.ForbiddenException;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.certificate.Certificate;
import com.imo.backend.modules.certificate.actions.CreateCertificateAction;
import com.imo.backend.modules.certificate.repositories.CertificateRepository;
import com.imo.backend.modules.certificate.values_objects.CertificatePeriod;
import com.imo.backend.modules.progress.Progress;
import com.imo.backend.modules.progress.repositories.ProgressRepository;
import com.imo.backend.modules.progress.value_objects.ProgressStatus;
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
