package com.imo.backend.modules.certificate.guards.impl;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.certificate.guards.GetCertificateDetailsByUserIdAndCourseIdGuard;
import com.imo.backend.modules.certificate.CertificateDetails;
import com.imo.backend.modules.certificate.repositories.CertificateRepository;
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
