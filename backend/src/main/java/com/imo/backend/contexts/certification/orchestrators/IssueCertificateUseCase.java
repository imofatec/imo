package com.imo.backend.contexts.certification.orchestrators;

import com.imo.backend.contexts.certification.Certificate;
import com.imo.backend.contexts.certification.CertificateDetails;
import com.imo.backend.contexts.certification.events.CertificateIssuedEvent;
import com.imo.backend.contexts.certification.repositories.CertificateRepository;
import com.imo.backend.contexts.certification.services.CreateCertificateService;
import com.imo.backend.contexts.certification.services.IssueCertificateService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IssueCertificateUseCase {
  private final CreateCertificateService createCertificateService;

  private final CertificateRepository certificateRepository;

  private final IssueCertificateService issueCertificateService;

  private final ApplicationEventPublisher applicationEventPublisher;

  public IssueCertificateUseCase(
      CreateCertificateService createCertificateService,
      CertificateRepository certificateRepository,
      IssueCertificateService issueCertificateService,
      ApplicationEventPublisher applicationEventPublisher
  ) {
    this.createCertificateService = createCertificateService;
    this.certificateRepository = certificateRepository;
    this.issueCertificateService = issueCertificateService;
    this.applicationEventPublisher = applicationEventPublisher;
  }

  @Transactional
  public IssueCertificateResult execute(String userId, String courseId) {
    Optional<CertificateDetails> certificateDetails = this.certificateRepository.findDetailsByUserIdAndCourseId(
        userId,
        courseId
    );

    if (certificateDetails.isEmpty()) {
      Certificate createdCertificate = this.createCertificateService.execute(userId, courseId);
      this.applicationEventPublisher.publishEvent(new CertificateIssuedEvent(
          createdCertificate.getId(),
          createdCertificate.getUserId(),
          createdCertificate.getCourseId(),
          createdCertificate.getIssuedAt()
      ));
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
