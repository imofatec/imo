package com.imo.backend.contexts.journey_tracking.progress;

import java.time.LocalDateTime;

public record ProgressPeriod(LocalDateTime startedAt, LocalDateTime finishedAt) {}
