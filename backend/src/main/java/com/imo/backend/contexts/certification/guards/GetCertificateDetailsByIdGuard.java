package com.imo.backend.contexts.certification.guards;

import com.imo.backend.contexts.certification.CertificateDetails;

public interface GetCertificateDetailsByIdGuard {
  CertificateDetails execute(String id);
}
