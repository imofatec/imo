package com.imo.backend.contexts.certification.http.dtos;

import com.imo.backend.contexts.certification.CertificateDetails;
import com.imo.backend.contexts.common.FormatDateTime;

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
            FormatDateTime.toDate(
                    certificateDetails.certificate().getCertificatePeriod().courseStartedAt())
                .replaceAll("-", "/"),
            FormatDateTime.toDate(
                    certificateDetails.certificate().getCertificatePeriod().courseFinishedAt())
                .replaceAll("-", "/")),
        FormatDateTime.toDateTime(certificateDetails.certificate().getIssuedAt())
            .replaceAll("-", "/"));
  }
}
