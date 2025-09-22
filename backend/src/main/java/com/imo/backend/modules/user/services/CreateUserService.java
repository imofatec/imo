package com.imo.backend.modules.user.services;

import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.actions.inputs.CreateUserInput;

public interface CreateUserService {
  User execute(CreateUserInput input);
}
