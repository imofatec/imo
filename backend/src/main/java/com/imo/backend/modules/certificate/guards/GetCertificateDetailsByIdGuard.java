package com.imo.backend.modules.certificate.guards;

import com.imo.backend.modules.certificate.CertificateDetails;

public interface GetCertificateDetailsByIdGuard {
  CertificateDetails execute(String id);
}
