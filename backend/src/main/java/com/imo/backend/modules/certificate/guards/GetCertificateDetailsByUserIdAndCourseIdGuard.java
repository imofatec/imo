package com.imo.backend.modules.certificate.guards;

import com.imo.backend.modules.certificate.CertificateDetails;

public interface GetCertificateDetailsByUserIdAndCourseIdGuard {
  CertificateDetails execute(String userId, String courseId);
}
