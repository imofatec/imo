package com.imo.backend.contexts.identity.usecases;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.commands.UpdateUserByIdCommand;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdatePasswordByIdUseCase {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public UpdatePasswordByIdUseCase(
      PasswordEncoder passwordEncoder,
      UserRepository userRepository
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public User execute(String userId, String newPassword) {
    var hashedPassword = passwordEncoder.encode(newPassword);

    var foundUser = userRepository.findById(userId).orElse(null);

    if (foundUser == null) {
      return null;
    }

    User.applyUpdate(foundUser, new UpdateUserByIdCommand(null, null, hashedPassword, null, null, null, null, null, null));

    return this.userRepository.save(foundUser);
  }
}
