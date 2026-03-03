package com.imo.backend.contexts.identity.actions;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.actions.inputs.CreateUserInput;

public interface CreateUserAction {
  User execute(CreateUserInput createUserInput);
}
