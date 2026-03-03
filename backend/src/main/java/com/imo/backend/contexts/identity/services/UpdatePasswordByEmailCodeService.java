package com.imo.backend.contexts.identity.services;

import com.imo.backend.contexts.identity.User;

public interface UpdatePasswordByEmailCodeService {
  User execute(String emailCode, String userId, String newPassword);
}
