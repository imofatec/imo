package com.imo.backend.contexts.certification.use_cases;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.imo.backend.contexts.certification.CertificateDetails;
import com.imo.backend.contexts.certification.repositories.CertificateRepository;
import com.imo.backend.contexts.certification.services.CreateCertificateService;
import com.imo.backend.contexts.certification.services.IssueCertificateService;

@Service
public class IssueCertificateUseCase {
  private final CreateCertificateService createCertificateService;

  private final CertificateRepository certificateRepository;

  private final IssueCertificateService issueCertificateService;

  public IssueCertificateUseCase(
      CreateCertificateService createCertificateService,
      CertificateRepository certificateRepository,
      IssueCertificateService issueCertificateService
  ) {
    this.createCertificateService = createCertificateService;
    this.certificateRepository = certificateRepository;
    this.issueCertificateService = issueCertificateService;
  }

  @Transactional
  public IssueCertificateResult execute(String userId, String courseId) {
    Optional<CertificateDetails> certificateDetails = this.certificateRepository.findDetailsByUserIdAndCourseId(
        userId,
        courseId
    );

    if (certificateDetails.isEmpty()) {
      this.createCertificateService.execute(userId, courseId);
    }

    CertificateDetails foundCertificateDetails = this.certificateRepository
        .findDetailsByUserIdAndCourseIdOrThrow(userId, courseId);
    byte[] pdf = this.issueCertificateService.execute(foundCertificateDetails);
    return new IssueCertificateResult(pdf, this.buildFilename(foundCertificateDetails));
  }

  private String buildFilename(CertificateDetails certificateDetails) {
    return String.format(
        "%s-%s-%s",
        certificateDetails.user().getName().toUpperCase(),
        certificateDetails.course().getName().slug().toUpperCase(),
        certificateDetails.certificate().getIssuedAt()
    );
  }

  public record IssueCertificateResult(byte[] pdf, String filename) {
  }
}
