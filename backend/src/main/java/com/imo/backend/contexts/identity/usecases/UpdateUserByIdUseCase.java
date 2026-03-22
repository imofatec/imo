package com.imo.backend.contexts.identity.usecases;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.commands.UpdateUserByIdCommand;
import com.imo.backend.contexts.identity.http.dtos.UpdateUserByIdRequest;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserByIdUseCase {

  private final PasswordEncoder passwordEncoder;

  private final UserRepository userRepository;

  public UpdateUserByIdUseCase(PasswordEncoder passwordEncoder, UserRepository userRepository) {
    this.passwordEncoder = passwordEncoder;
    this.userRepository = userRepository;
  }

  public User execute(String id, UpdateUserByIdRequest fieldsToUpdateUser) {
    UpdateUserByIdCommand cmd = new UpdateUserByIdCommand(
        fieldsToUpdateUser.email(),
        fieldsToUpdateUser.name(),
        fieldsToUpdateUser.password() != null
            ? this.passwordEncoder.encode(fieldsToUpdateUser.password())
            : null,
        null,
        User.getBirthDateFromString(fieldsToUpdateUser.birthDate()),
        fieldsToUpdateUser.availableTimePerDay(),
        fieldsToUpdateUser.academicDegree(),
        fieldsToUpdateUser.experienceLevel(),
        fieldsToUpdateUser.categoriesOfInterest()
    );

    var foundUser = userRepository.findById(id).orElse(null);

    if (foundUser == null) {
      return null;
    }

    User.applyUpdate(foundUser, cmd);

    return this.userRepository.save(foundUser);
  }
}
