package com.imo.backend.contexts.journey_tracking.actions.helpers;

import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.commands.UpdateProgressCommand;

public class ProgressUpdater {
  public static void apply(Progress progress, UpdateProgressCommand cmd) {
    if (cmd.progressPeriod() != null) {
      progress.setProgressPeriod(cmd.progressPeriod());
    }

    if (cmd.status() != null) {
      progress.setStatus(cmd.status());
    }

    if (cmd.lessonsWatched() != null) {
      progress.setLessonsWatched(cmd.lessonsWatched());
    }
  }
}
