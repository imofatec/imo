package com.imo.backend.contexts.identity.usecases;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.commands.UpdateUserByIdCommand;
import com.imo.backend.contexts.identity.http.dtos.UpdateUserByIdRequest;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserByIdUseCase {

  private final UpdatePasswordByIdUseCase updatePasswordByIdUseCase;

  private final UserRepository userRepository;

  public UpdateUserByIdUseCase(
      UpdatePasswordByIdUseCase updatePasswordByIdUseCase,
      UserRepository userRepository
  ) {
    this.updatePasswordByIdUseCase = updatePasswordByIdUseCase;
    this.userRepository = userRepository;
  }

  public User execute(String id, UpdateUserByIdRequest fieldsToUpdateUser) {
    if (fieldsToUpdateUser.password() != null) {
      this.updatePasswordByIdUseCase.execute(id, fieldsToUpdateUser.password());
    }

    UpdateUserByIdCommand cmd = new UpdateUserByIdCommand(
        fieldsToUpdateUser.email(),
        fieldsToUpdateUser.name(),
        null,
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
