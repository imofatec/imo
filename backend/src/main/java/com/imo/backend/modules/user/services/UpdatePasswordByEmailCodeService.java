package com.imo.backend.modules.user.services;

import com.imo.backend.modules.user.User;

public interface UpdatePasswordByEmailCodeService {
  User execute(String emailCode, String userId, String newPassword);
}
