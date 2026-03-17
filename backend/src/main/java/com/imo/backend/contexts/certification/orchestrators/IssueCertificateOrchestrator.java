package com.imo.backend.contexts.certification.orchestrators;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.certification.actions.CreateCertificateAction;
import com.imo.backend.contexts.certification.repositories.CertificateRepository;
import com.imo.backend.contexts.certification.CertificateDetails;
import com.imo.backend.contexts.certification.services.IssueCertificateService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


@Service
public class IssueCertificateOrchestrator {
  private final CreateCertificateAction createCertificateAction;

  private final CertificateRepository certificateRepository;

  private final IssueCertificateService issueCertificateService;

  public IssueCertificateOrchestrator(
      CreateCertificateAction createCertificateAction,
      CertificateRepository certificateRepository,
      IssueCertificateService issueCertificateService
  ) {
    this.createCertificateAction = createCertificateAction;
    this.certificateRepository = certificateRepository;
    this.issueCertificateService = issueCertificateService;
  }

  public byte[] execute(String userId, String courseId, HttpHeaders headers) {
    CertificateDetails certificateDetails = this.certificateRepository
        .findDetailsByUserIdAndCourseId(userId, courseId).orElse(null);

    if (certificateDetails == null) {
      this.createCertificateAction.execute(userId, courseId);
      certificateDetails = this.certificateRepository
          .findDetailsByUserIdAndCourseId(userId, courseId).orElseThrow(() -> new NotFoundException("Certificado não encontrado"));
    }

    this.manageHeaders(certificateDetails, headers);

    return this.issueCertificateService.execute(certificateDetails);
  }

  private void manageHeaders(CertificateDetails certificateDetails, HttpHeaders headers) {
    var filename = String.format(
        "%s-%s-%s",
        certificateDetails.user().getName().toUpperCase(),
        certificateDetails.course().getName().slug().toUpperCase(),
        certificateDetails.certificate().getIssuedAt()
    );

    var headerValue = String.format("attachment; filename=%s.pdf", filename);

    headers.add(HttpHeaders.CONTENT_DISPOSITION, headerValue);
  }
}
