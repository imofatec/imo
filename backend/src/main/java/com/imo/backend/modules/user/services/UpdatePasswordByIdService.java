package com.imo.backend.modules.user.services;

import com.imo.backend.modules.user.User;

public interface UpdatePasswordByIdService {
  User execute(String userId, String newPassword);
}
