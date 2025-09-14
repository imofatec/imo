package com.imo.backend.modules.user.services;

import com.imo.backend.modules.user.User;

public interface SendForgetPasswordCodeService {
  User execute(String email);
}
