package com.imo.backend.modules.progress.actions.helpers;

import com.imo.backend.modules.progress.Progress;
import com.imo.backend.modules.progress.actions.inputs.UpdateProgressInput;

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
