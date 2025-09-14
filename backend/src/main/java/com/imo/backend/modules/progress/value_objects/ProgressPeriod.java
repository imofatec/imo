package com.imo.backend.modules.progress.value_objects;

import java.time.LocalDateTime;

public record ProgressPeriod(
    LocalDateTime startedAt,
    LocalDateTime finishedAt
) {
}
