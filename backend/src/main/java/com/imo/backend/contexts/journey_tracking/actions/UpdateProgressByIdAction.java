package com.imo.backend.contexts.journey_tracking.actions;

import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.actions.inputs.UpdateProgressInput;

public interface UpdateProgressByIdAction {
  Progress execute(String id, UpdateProgressInput input);
}
