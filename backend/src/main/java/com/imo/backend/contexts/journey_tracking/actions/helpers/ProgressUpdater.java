package com.imo.backend.contexts.journey_tracking.actions.helpers;

import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.actions.inputs.UpdateProgressInput;

public class ProgressUpdater {
  public static void apply(Progress progress, UpdateProgressInput dto) {
    if (dto.progressPeriod() != null) {
      progress.setProgressPeriod(dto.progressPeriod());
    }

    if (dto.status() != null) {
      progress.setStatus(dto.status());
    }

    if (dto.lessonsWatched() != null) {
      progress.setLessonsWatched(dto.lessonsWatched());
    }
  }
}
