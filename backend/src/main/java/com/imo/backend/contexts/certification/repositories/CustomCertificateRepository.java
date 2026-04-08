package com.imo.backend.contexts.certification.repositories;

import com.imo.backend.contexts.certification.CertificateDetails;
import java.util.Optional;

public interface CustomCertificateRepository {
  Optional<CertificateDetails> findDetailsByUserIdAndCourseId(String userId, String courseId);

  Optional<CertificateDetails> findDetailsById(String id);
}
