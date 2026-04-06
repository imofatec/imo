package com.imo.backend.contexts.identity.actions.impl;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ConflictException;
import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.actions.CreateUserAction;
import com.imo.backend.contexts.identity.actions.inputs.CreateUserInput;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserActionImpl implements CreateUserAction {
  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public CreateUserActionImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User execute(CreateUserInput createUserInput) {
    this.checkCredentials(createUserInput);

    var potentialNewUser =
        new User(
            createUserInput.name(), createUserInput.email(), createUserInput.password(), false);

    potentialNewUser.setPassword(passwordEncoder.encode(potentialNewUser.getPassword()));

    return userRepository.save(potentialNewUser);
  }

  private void checkCredentials(CreateUserInput createUserInput) {
    var userEmail = userRepository.findByEmail(createUserInput.email());
    if (userEmail.isPresent()) {
      throw new ConflictException("O email já existe");
    }

    if (!createUserInput.confPassword().equals(createUserInput.password())) {
      throw new BadRequestException("As senhas não coincidem");
    }
  }
}
