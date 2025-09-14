package com.imo.backend.modules.certificate.services;

import com.imo.backend.modules.certificate.CertificateDetails;

public interface IssueCertificateService {
  byte[] execute(CertificateDetails certificateDetails);
}
