package com.imo.backend.contexts.identity.usecases;

import com.imo.backend.contexts.identity.UserPolicies;
import com.imo.backend.contexts.identity.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.contexts.identity.http.dtos.auth.LoginResponseDTO;
import com.imo.backend.contexts.identity.lib.TokenManager;
import com.imo.backend.contexts.identity.repositories.UserRepository;

import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserUseCase {
  private final UserRepository userRepository;

  private final UserPolicies policies;

  private final TokenManager tokenManager;

  public AuthenticateUserUseCase(
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
