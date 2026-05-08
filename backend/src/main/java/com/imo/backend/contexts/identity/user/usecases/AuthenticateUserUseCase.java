package com.imo.backend.contexts.identity.user.usecases;

import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.identity.user.UserPolicies;
import com.imo.backend.contexts.identity.user.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.contexts.identity.user.http.dtos.auth.LoginResponseDTO;
import com.imo.backend.contexts.identity.user.lib.TokenManager;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserUseCase {
  private final UserPolicies policies;

  private final TokenManager tokenManager;

  public AuthenticateUserUseCase(TokenManager tokenManager, UserPolicies policies) {
    this.tokenManager = tokenManager;
    this.policies = policies;
  }

  public LoginResponseDTO execute(LoginRequestDTO loginRequestDTO) {
    User foundUser =
        this.policies.assertCredentials(loginRequestDTO.email(), loginRequestDTO.password());

    return tokenManager.generateToken(foundUser.getId());
  }
}
