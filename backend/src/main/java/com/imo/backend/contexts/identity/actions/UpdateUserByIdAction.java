package com.imo.backend.contexts.identity.actions;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.actions.inputs.UpdateUserByIdInput;

public interface UpdateUserByIdAction {
  User execute(String id, UpdateUserByIdInput command);
}
