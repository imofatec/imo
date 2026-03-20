package com.imo.backend.contexts.identity.services.impl;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.commands.UpdateUserByIdCommand;
import com.imo.backend.contexts.identity.http.dtos.UpdateUserByIdRequest;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import com.imo.backend.contexts.identity.services.UpdatePasswordByIdService;
import com.imo.backend.contexts.identity.services.UpdateUserByIdService;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserByIdServiceImpl implements UpdateUserByIdService {

  private final UpdatePasswordByIdService updatePasswordByIdService;

  private final UserRepository userRepository;

  public UpdateUserByIdServiceImpl(
      UpdatePasswordByIdService updatePasswordByIdService,
      UserRepository userRepository
  ) {
    this.updatePasswordByIdService = updatePasswordByIdService;
    this.userRepository = userRepository;
  }

  public User execute(String id, UpdateUserByIdRequest fieldsToUpdateUser) {
    if (fieldsToUpdateUser.password() != null) {
      this.updatePasswordByIdService.execute(id, fieldsToUpdateUser.password());
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
