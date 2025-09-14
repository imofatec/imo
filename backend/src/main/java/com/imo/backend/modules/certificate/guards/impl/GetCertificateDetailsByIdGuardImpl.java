package com.imo.backend.modules.certificate.guards.impl;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.certificate.guards.GetCertificateDetailsByIdGuard;
import com.imo.backend.modules.certificate.CertificateDetails;
import com.imo.backend.modules.certificate.repositories.CertificateRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCertificateDetailsByIdGuardImpl implements GetCertificateDetailsByIdGuard {
  private final CertificateRepository certificateRepository;

  public GetCertificateDetailsByIdGuardImpl(CertificateRepository certificateRepository) {
    this.certificateRepository = certificateRepository;
  }

  @Override
  public CertificateDetails execute(String id) {
    return this.certificateRepository
        .findDetailsById(id)
        .orElseThrow(() -> new NotFoundException("Certiicado não encontrado"));
  }
}
