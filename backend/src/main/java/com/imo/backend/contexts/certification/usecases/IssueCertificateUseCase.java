package com.imo.backend.contexts.certification.usecases;

import com.imo.backend.contexts.certification.Certificate;
import com.imo.backend.contexts.certification.CertificateDetails;
import com.imo.backend.contexts.certification.CertificatePolicies;
import com.imo.backend.contexts.certification.lib.PdfManager;
import com.imo.backend.contexts.certification.repositories.CertificateRepository;
import com.imo.backend.contexts.certification.values_objects.CertificatePeriod;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.journey_tracking.Progress;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class IssueCertificateUseCase {
  private final CertificateRepository certificateRepository;
  private final CertificatePolicies certificatePolicies;
  private final PdfManager pdfManager;

  public IssueCertificateUseCase(
      CertificateRepository certificateRepository,
      CertificatePolicies certificatePolicies,
      PdfManager pdfManager
  ) {
    this.certificateRepository = certificateRepository;
    this.certificatePolicies = certificatePolicies;
    this.pdfManager = pdfManager;
  }

  public byte[] execute(String userId, String courseId, HttpHeaders headers) {
    CertificateDetails certificateDetails = this.certificateRepository
        .findDetailsByUserIdAndCourseId(userId, courseId)
        .orElse(null);

    if (certificateDetails == null) {
      Progress progress = this.certificatePolicies.assertCourseIsFinished(userId, courseId);

      this.certificateRepository.save(new Certificate(
          userId,
          courseId,
          new CertificatePeriod(
              progress.getProgressPeriod().startedAt(),
              progress.getProgressPeriod().finishedAt()
          ),
          LocalDateTime.now()
      ));

      certificateDetails = this.certificateRepository
          .findDetailsByUserIdAndCourseId(userId, courseId)
          .orElseThrow(() -> new NotFoundException("Certificado não encontrado"));
    }

    this.manageHeaders(certificateDetails, headers);
    return this.pdfManager.execute(certificateDetails);
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
