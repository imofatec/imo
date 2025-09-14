package com.imo.backend.modules.progress.actions;

import com.imo.backend.modules.progress.Progress;
import com.imo.backend.modules.progress.actions.inputs.UpdateProgressInput;

public interface UpdateProgressByIdAction {
  Progress execute(String id, UpdateProgressInput input);
}
