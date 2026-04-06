package com.imo.backend.contexts.identity.services.impl;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.contexts.identity.http.dtos.auth.LoginResponseDTO;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import com.imo.backend.contexts.identity.services.AuthenticateUserService;
import com.imo.backend.lib.token.TokenManager;
import java.util.Optional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticateUserServiceImpl implements AuthenticateUserService {
  private final UserRepository userRepository;

  private final TokenManager tokenManager;

  private final PasswordEncoder passwordEncoder;

  public AuthenticateUserServiceImpl(
      UserRepository userRepository, TokenManager tokenManager, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.tokenManager = tokenManager;
    this.passwordEncoder = passwordEncoder;
  }

  public LoginResponseDTO execute(LoginRequestDTO loginRequestDTO) {
    var foundUser = userRepository.findByEmail(loginRequestDTO.email());
    assert foundUser.isPresent();

    this.checkCredentials(foundUser, loginRequestDTO);

    return tokenManager.generateToken(foundUser.get().getId());
  }

  private void checkCredentials(Optional<User> foundUser, LoginRequestDTO loginRequestDTO) {
    if (foundUser.isEmpty()) {
      throw new BadRequestException("Credenciais inválidas");
    }

    var isMatch =
        passwordEncoder.matches(loginRequestDTO.password(), foundUser.get().getPassword());

    if (!isMatch) {
      throw new BadRequestException("Credenciais inválidas");
    }
  }
}
