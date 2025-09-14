package com.imo.backend.modules.user.guards.impl;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.guards.GetUserByIdGuard;
import com.imo.backend.modules.user.repositories.UserRepository;
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
