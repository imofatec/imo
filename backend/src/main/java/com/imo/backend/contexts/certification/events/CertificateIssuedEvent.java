package com.imo.backend.contexts.certification.events;

import java.time.LocalDateTime;

public record CertificateIssuedEvent(
    String certificateId,
    String userId,
    String courseId,
    LocalDateTime issuedAt
) {
}
