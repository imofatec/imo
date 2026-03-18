package com.imo.backend.contexts.certification.actions.impl;

import com.imo.backend.contexts.certification.Certificate;
import com.imo.backend.contexts.certification.CertificatePolicies;
import com.imo.backend.contexts.certification.actions.CreateCertificateAction;
import com.imo.backend.contexts.certification.repositories.CertificateRepository;
import com.imo.backend.contexts.certification.values_objects.CertificatePeriod;
import com.imo.backend.contexts.journey_tracking.Progress;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CreateCertificateActionImpl implements CreateCertificateAction {
  private final CertificatePolicies certificatePolicies;

  private final CertificateRepository certificateRepository;

  public CreateCertificateActionImpl(
      CertificatePolicies certificatePolicies,
      CertificateRepository certificateRepository
  ) {
    this.certificatePolicies = certificatePolicies;
    this.certificateRepository = certificateRepository;
  }

  @Override
  public Certificate execute(String userId, String courseId) {
    Progress progress = this.certificatePolicies.assertCourseIsFinished(userId, courseId);

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
