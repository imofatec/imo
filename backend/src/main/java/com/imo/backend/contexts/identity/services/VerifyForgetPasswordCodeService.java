package com.imo.backend.contexts.identity.services;

import com.imo.backend.contexts.identity.User;

public interface VerifyForgetPasswordCodeService {
  User execute(String userId, String code);
}
