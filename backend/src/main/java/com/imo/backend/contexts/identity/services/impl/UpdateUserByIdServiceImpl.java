package com.imo.backend.contexts.identity.services.impl;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.actions.UpdateUserByIdAction;
import com.imo.backend.contexts.identity.actions.inputs.UpdateUserByIdInput;
import com.imo.backend.contexts.identity.http.dtos.UpdateUserByIdRequest;
import com.imo.backend.contexts.identity.services.UpdatePasswordByIdService;
import com.imo.backend.contexts.identity.services.UpdateUserByIdService;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserByIdServiceImpl implements UpdateUserByIdService {
  private final UpdateUserByIdAction updateUserByIdAction;

  private final UpdatePasswordByIdService updatePasswordByIdService;

  public UpdateUserByIdServiceImpl(
      UpdateUserByIdAction updateUserByIdAction,
      UpdatePasswordByIdService updatePasswordByIdService) {
    this.updateUserByIdAction = updateUserByIdAction;
    this.updatePasswordByIdService = updatePasswordByIdService;
  }

  public User execute(String id, UpdateUserByIdRequest fieldsToUpdateUser) {
    if (fieldsToUpdateUser.password() != null) {
      this.updatePasswordByIdService.execute(id, fieldsToUpdateUser.password());
    }

    UpdateUserByIdInput input =
        new UpdateUserByIdInput(
            fieldsToUpdateUser.email(),
            fieldsToUpdateUser.name(),
            null,
            null,
            User.getBirthDateFromString(fieldsToUpdateUser.birthDate()),
            fieldsToUpdateUser.availableTimePerDay(),
            fieldsToUpdateUser.academicDegree(),
            fieldsToUpdateUser.experienceLevel(),
            fieldsToUpdateUser.categoriesOfInterest());
    return this.updateUserByIdAction.execute(id, input);
  }
}
