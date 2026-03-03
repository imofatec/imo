package com.imo.backend.contexts.identity.services;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.http.dtos.UpdateUserByIdRequest;

public interface UpdateUserByIdService {
  User execute(String id, UpdateUserByIdRequest fieldsToUpdateUser);
}
