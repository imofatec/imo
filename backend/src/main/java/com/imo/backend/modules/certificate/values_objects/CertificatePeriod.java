package com.imo.backend.modules.certificate.values_objects;

import java.time.LocalDateTime;

public record CertificatePeriod(
    LocalDateTime courseStartedAt,
    LocalDateTime courseFinishedAt
) {
}
