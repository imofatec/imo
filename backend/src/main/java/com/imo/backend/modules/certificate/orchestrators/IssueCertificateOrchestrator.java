package com.imo.backend.modules.certificate.orchestrators;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.certificate.actions.CreateCertificateAction;
import com.imo.backend.modules.certificate.guards.GetCertificateDetailsByUserIdAndCourseIdGuard;
import com.imo.backend.modules.certificate.CertificateDetails;
import com.imo.backend.modules.certificate.services.IssueCertificateService;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;


@Service
public class IssueCertificateOrchestrator {
  private final CreateCertificateAction createCertificateAction;

  private final GetCertificateDetailsByUserIdAndCourseIdGuard getCertificateDetailsByUserIdAndCourseId;

  private final IssueCertificateService issueCertificateService;

  public IssueCertificateOrchestrator(
      CreateCertificateAction createCertificateAction,
      GetCertificateDetailsByUserIdAndCourseIdGuard getCertificateDetailsByUserIdAndCourseId,
      IssueCertificateService issueCertificateService
  ) {
    this.createCertificateAction = createCertificateAction;
    this.getCertificateDetailsByUserIdAndCourseId = getCertificateDetailsByUserIdAndCourseId;
    this.issueCertificateService = issueCertificateService;
  }

  public byte[] execute(String userId, String courseId, HttpHeaders headers) {
    CertificateDetails certificateDetails = null;

    try {
      certificateDetails = this.getCertificateDetailsByUserIdAndCourseId.execute(userId, courseId);
    } catch (NotFoundException ignored) {
    }

    if (certificateDetails == null) {
      this.createCertificateAction.execute(userId, courseId);
      certificateDetails = this.getCertificateDetailsByUserIdAndCourseId.execute(userId, courseId);
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
