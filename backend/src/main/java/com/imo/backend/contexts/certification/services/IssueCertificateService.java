package com.imo.backend.contexts.certification.services;

import com.imo.backend.contexts.certification.CertificateDetails;

public interface IssueCertificateService {
  byte[] execute(CertificateDetails certificateDetails);
}
