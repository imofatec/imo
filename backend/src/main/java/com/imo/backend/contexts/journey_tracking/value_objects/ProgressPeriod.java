package com.imo.backend.contexts.journey_tracking.value_objects;

import java.time.LocalDateTime;

public record ProgressPeriod(
    LocalDateTime startedAt,
    LocalDateTime finishedAt
) {
}
