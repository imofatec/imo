package com.imo.backend.modules.user.actions;

import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.actions.inputs.UpdateUserByIdInput;

public interface UpdateUserByIdAction {
  User execute(String id, UpdateUserByIdInput command);
}
