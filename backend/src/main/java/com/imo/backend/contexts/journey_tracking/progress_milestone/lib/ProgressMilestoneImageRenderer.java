package com.imo.backend.contexts.journey_tracking.progress_milestone.lib;

import com.imo.backend.contexts.journey_tracking.progress_milestone.ProgressMilestone;

public interface ProgressMilestoneImageRenderer {
  byte[] execute(ProgressMilestone milestone);
}
