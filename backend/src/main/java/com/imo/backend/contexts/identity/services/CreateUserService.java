package com.imo.backend.contexts.identity.services;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.commands.CreateUserCommand;

public interface CreateUserService {
  User execute(CreateUserCommand createUserCommand);
}
