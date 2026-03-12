package com.imo.backend.contexts.certification.guards;

import com.imo.backend.contexts.certification.CertificateDetails;

public interface GetCertificateDetailsByUserIdAndCourseIdGuard {
  CertificateDetails execute(String userId, String courseId);
}
