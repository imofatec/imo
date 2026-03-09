package com.imo.backend.contexts.journey_tracking.use_cases.helpers;

import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.use_cases.commands.UpdateProgressCommand;

public class ProgressUpdater {
  public static void apply(Progress progress, UpdateProgressCommand command) {
    if (command.progressPeriod() != null) {
      progress.setProgressPeriod(command.progressPeriod());
    }

    if (command.status() != null) {
      progress.setStatus(command.status());
    }

    if (command.lessonsWatched() != null) {
      progress.setLessonsWatched(command.lessonsWatched());
    }
  }
}
