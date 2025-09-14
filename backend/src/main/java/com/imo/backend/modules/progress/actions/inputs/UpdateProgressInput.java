package com.imo.backend.modules.progress.actions.inputs;

import com.imo.backend.modules.progress.value_objects.ProgressPeriod;
import com.imo.backend.modules.progress.value_objects.ProgressStatus;

import java.util.List;

public record UpdateProgressInput(
    ProgressPeriod progressPeriod,
    ProgressStatus status,
    List<String> lessonsWatched
) {
}
