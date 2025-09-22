package com.imo.backend.modules.user.actions.impl;

import com.imo.backend.exceptions.custom.BadRequestException;
import com.imo.backend.exceptions.custom.ConflictException;
import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.actions.CreateUserAction;
import com.imo.backend.modules.user.actions.inputs.CreateUserInput;
import com.imo.backend.modules.user.repositories.UserRepository;
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

    var potentialNewUser = new User(
        createUserInput.name(),
        createUserInput.email(),
        createUserInput.password(),
        false
    );

    potentialNewUser.setPassword(passwordEncoder.encode(potentialNewUser.getPassword()));

    return userRepository.save(potentialNewUser);
  }

  private void checkCredentials(CreateUserInput createUserInput) {
    var userEmail = userRepository.findByEmail(createUserInput.email());
    if (userEmail.isPresent()) {
      throw new ConflictException("O email já existe");
    }

    if (!createUserInput.confPassword().matches(createUserInput.password())) {
      throw new BadRequestException("As senhas não coincidem");
    }
  }
}
