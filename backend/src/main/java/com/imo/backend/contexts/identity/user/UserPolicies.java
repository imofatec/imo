package com.imo.backend.contexts.identity.user;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.identity.user.commands.CreateUserCommand;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPolicies {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public UserPolicies(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void assertCanRegister(CreateUserCommand createUserCommand) {
    var userEmail = userRepository.findByEmail(createUserCommand.email());
    if (userEmail.isPresent()) {
      throw new ConflictException("O email já existe");
    }

    if (!createUserCommand.confPassword().equals(createUserCommand.password())) {
      throw new BadRequestException("As senhas não coincidem");
    }
  }

  public void assertCredentials(Optional<User> foundUser, String password) {
    if (foundUser.isEmpty()) {
      throw new BadRequestException("Credenciais inválidas");
    }

    var isMatch = passwordEncoder.matches(password, foundUser.get().getPassword());

    if (!isMatch) {
      throw new BadRequestException("Credenciais inválidas");
    }
  }
}
