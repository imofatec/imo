package com.imo.backend.contexts.identity.services.impl;

import com.imo.backend.contexts.identity.UserPolicies;
import com.imo.backend.contexts.identity.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.contexts.identity.http.dtos.auth.LoginResponseDTO;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import com.imo.backend.contexts.identity.services.AuthenticateUserService;
import com.imo.backend.lib.token.TokenManager;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserServiceImpl implements AuthenticateUserService {
  private final UserRepository userRepository;

  private final UserPolicies policies;

  private final TokenManager tokenManager;

  public AuthenticateUserServiceImpl(
      UserRepository userRepository,
      TokenManager tokenManager,
      UserPolicies policies
  ) {
    this.userRepository = userRepository;
    this.tokenManager = tokenManager;
    this.policies = policies;
  }

  public LoginResponseDTO execute(LoginRequestDTO loginRequestDTO) {
    var foundUser = userRepository.findByEmail(loginRequestDTO.email());
    assert foundUser.isPresent();

    this.policies.assertCredentials(foundUser, loginRequestDTO.password());

    return tokenManager.generateToken(foundUser.get().getId());
  }
}
