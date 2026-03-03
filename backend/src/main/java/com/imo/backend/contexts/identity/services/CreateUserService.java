package com.imo.backend.contexts.identity.services;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.actions.inputs.CreateUserInput;

public interface CreateUserService {
  User execute(CreateUserInput input);
}
