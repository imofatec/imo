package com.imo.backend.contexts.journey_tracking.actions.inputs;

import com.imo.backend.contexts.journey_tracking.value_objects.ProgressPeriod;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import java.util.List;

public record UpdateProgressInput(
    ProgressPeriod progressPeriod, ProgressStatus status, List<String> lessonsWatched) {}
