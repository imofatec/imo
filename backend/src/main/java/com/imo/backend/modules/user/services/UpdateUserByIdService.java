package com.imo.backend.modules.user.services;

import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.http.dtos.UpdateUserByIdRequest;

public interface UpdateUserByIdService {
  User execute(String id, UpdateUserByIdRequest fieldsToUpdateUser);
}
