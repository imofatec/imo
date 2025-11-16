package com.imo.backend.modules.certificate.repositories;

import com.imo.backend.modules.certificate.CertificateDetails;

import java.util.Optional;

public interface CustomCertificateRepository {
  Optional<CertificateDetails> findDetailsByUserIdAndCourseId(String userId, String courseId);

  Optional<CertificateDetails> findDetailsById(String id);
}
