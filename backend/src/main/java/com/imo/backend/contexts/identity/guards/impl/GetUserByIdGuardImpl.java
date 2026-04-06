package com.imo.backend.contexts.identity.guards.impl;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.guards.GetUserByIdGuard;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetUserByIdGuardImpl implements GetUserByIdGuard {
  private final UserRepository userRepository;

  public GetUserByIdGuardImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(String id) {
    return this.userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
  }
}
