package com.imo.backend.contexts.identity.services;

import com.imo.backend.contexts.identity.User;

public interface UpdatePasswordByIdService {
  User execute(String userId, String newPassword);
}
