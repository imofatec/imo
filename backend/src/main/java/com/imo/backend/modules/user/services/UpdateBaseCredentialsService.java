package com.imo.backend.modules.user.services;

import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.actions.inputs.UpdateBaseCredentialsInput;

public interface UpdateBaseCredentialsService {
  User execute(String id, UpdateBaseCredentialsInput fieldsToUpdateUser);
}
