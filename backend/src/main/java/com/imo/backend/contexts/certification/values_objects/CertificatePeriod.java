package com.imo.backend.contexts.certification.values_objects;

import java.time.LocalDateTime;

public record CertificatePeriod(LocalDateTime courseStartedAt, LocalDateTime courseFinishedAt) {}
