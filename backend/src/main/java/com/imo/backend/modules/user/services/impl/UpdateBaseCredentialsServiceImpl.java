package com.imo.backend.modules.user.services.impl;

import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.actions.UpdateUserByIdAction;
import com.imo.backend.modules.user.actions.inputs.UpdateBaseCredentialsInput;
import com.imo.backend.modules.user.actions.inputs.UpdateUserByIdInput;
import com.imo.backend.modules.user.services.UpdateBaseCredentialsService;
import com.imo.backend.modules.user.services.UpdatePasswordByIdService;
import org.springframework.stereotype.Service;

@Service
public class UpdateBaseCredentialsServiceImpl implements UpdateBaseCredentialsService {
  private final UpdateUserByIdAction updateUserByIdAction;

  private final UpdatePasswordByIdService updatePasswordByIdService;

  public UpdateBaseCredentialsServiceImpl(
      UpdateUserByIdAction updateUserByIdAction,
      UpdatePasswordByIdService updatePasswordByIdService) {
    this.updateUserByIdAction = updateUserByIdAction;
    this.updatePasswordByIdService = updatePasswordByIdService;
  }

  public User execute(String id, UpdateBaseCredentialsInput fieldsToUpdateUser) {
    if (fieldsToUpdateUser.password() != null) {
      this.updatePasswordByIdService.execute(id, fieldsToUpdateUser.password());
    }

    return this.updateUserByIdAction.execute(
        id,
        new UpdateUserByIdInput(fieldsToUpdateUser.email(), fieldsToUpdateUser.name(), null, null));
  }
}
