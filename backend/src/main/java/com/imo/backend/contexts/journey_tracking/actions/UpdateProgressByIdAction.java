package com.imo.backend.contexts.journey_tracking.actions;

import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.commands.UpdateProgressCommand;

public interface UpdateProgressByIdAction {
  Progress execute(String id, UpdateProgressCommand cmd);
}
