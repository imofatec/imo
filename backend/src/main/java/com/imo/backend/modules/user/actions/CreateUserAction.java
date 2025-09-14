package com.imo.backend.modules.user.actions;

import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.actions.inputs.CreateUserInput;

public interface CreateUserAction {
  User execute(CreateUserInput createUserInput);
}
