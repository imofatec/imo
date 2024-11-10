package com.imo.backend.models.certificate.dtos;

import com.imo.backend.lib.FormatDateTime;
import com.imo.backend.models.certificate.Certificate;
import lombok.Data;

@Data
public class FormattedCertificate {

    private String name;

    private String courseName;

    private String courseStartedAt;

    private String courseFinishedAt;

    private String issuedAt;

    public static FormattedCertificate fromCertificate(Certificate certificate) {
        FormattedCertificate formattedCertificate = new FormattedCertificate();

        formattedCertificate.setName(certificate.getName().toUpperCase());
        formattedCertificate.setCourseName(certificate.getCourseName());

        var startedAt = FormatDateTime.toDate(certificate.getCourseStartedAt())
                .replaceAll("-", "/");

        var finishedAt = FormatDateTime.toDate(certificate.getCourseFinishedAt())
                .replaceAll("-", "/");

        var issuedAt = FormatDateTime.toDateTime(certificate.getIssuedAt())
                .replaceAll("-", "/");

        formattedCertificate.setCourseStartedAt(startedAt);
        formattedCertificate.setCourseFinishedAt(finishedAt);
        formattedCertificate.setIssuedAt(issuedAt);

        return formattedCertificate;
    }
}
