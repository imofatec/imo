package com.imo.backend.contexts.certification.guards.impl;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.certification.guards.GetCertificateDetailsByIdGuard;
import com.imo.backend.contexts.certification.CertificateDetails;
import com.imo.backend.contexts.certification.repositories.CertificateRepository;
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
