package com.imo.backend.contexts.certification.guards.impl;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.certification.guards.GetCertificateDetailsByUserIdAndCourseIdGuard;
import com.imo.backend.contexts.certification.CertificateDetails;
import com.imo.backend.contexts.certification.repositories.CertificateRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCertificateDetailsByUserIdAndCourseIdGuardImpl
    implements GetCertificateDetailsByUserIdAndCourseIdGuard {
  private final CertificateRepository certificateRepository;

  public GetCertificateDetailsByUserIdAndCourseIdGuardImpl(CertificateRepository certificateRepository) {
    this.certificateRepository = certificateRepository;
  }

  @Override
  public CertificateDetails execute(String userId, String courseId) {
    return this.certificateRepository
        .findDetailsByUserIdAndCourseId(userId, courseId)
        .orElseThrow(() -> new NotFoundException("Certificado não encontrado"));
  }
}
