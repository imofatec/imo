package com.imo.backend.contexts.identity.user;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.identity.user.commands.CreateUserCommand;
import com.imo.backend.contexts.identity.user.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserPolicies {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public UserPolicies(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void assertCanRegister(CreateUserCommand createUserCommand) {
    this.assertEmailAvailable(createUserCommand.email(), null);

    if (!createUserCommand.confPassword().equals(createUserCommand.password())) {
      throw new BadRequestException("As senhas não coincidem");
    }
  }

  public User assertCredentials(String email, String password) {
    User foundUser = this.userRepository.findByEmail(email).orElse(null);

    if (foundUser == null) {
      throw new BadRequestException("Credenciais inválidas");
    }

    var isMatch = passwordEncoder.matches(password, foundUser.getPassword());

    if (!isMatch) {
      throw new BadRequestException("Credenciais inválidas");
    }

    return foundUser;
  }

  public void assertCurrentPassword(User user, String oldPassword) {
    if (oldPassword == null || oldPassword.isEmpty()) {
      throw new BadRequestException("Informe a senha atual");
    }

    if (!this.passwordEncoder.matches(oldPassword, user.getPassword())) {
      throw new BadRequestException("A senha atual está incorreta");
    }
  }

  public void assertEmailAvailable(String email, String currentUserId) {
    User existingUser = this.userRepository.findByEmail(email).orElse(null);

    if (existingUser == null) {
      return;
    }

    if (currentUserId != null && currentUserId.equals(existingUser.getId())) {
      return;
    }

    throw new ConflictException("O email já existe");
  }
}
