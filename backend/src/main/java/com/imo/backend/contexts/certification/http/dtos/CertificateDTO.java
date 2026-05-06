package com.imo.backend.contexts.certification.http.dtos;

import com.imo.backend.contexts.certification.CertificateDetails;
import com.imo.backend.contexts.common.HttpDateTimeFormatter;

public record CertificateDTO(
    String username,
    String courseName,
    CertificatePeriodDTO certificatePeriodDTO,
    String issuedAt) {
  public static CertificateDTO fromCertificateDetails(CertificateDetails certificateDetails) {
    return new CertificateDTO(
        certificateDetails.user().getName().toUpperCase(),
        certificateDetails.course().getName().name().toUpperCase(),
        new CertificatePeriodDTO(
            HttpDateTimeFormatter.toDate(
                certificateDetails.certificate().getCertificatePeriod().courseStartedAt()),
            HttpDateTimeFormatter.toDate(
                certificateDetails.certificate().getCertificatePeriod().courseFinishedAt())),
        HttpDateTimeFormatter.toDateTime(certificateDetails.certificate().getIssuedAt()));
  }
}
