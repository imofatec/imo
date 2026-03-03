package com.imo.backend.contexts.identity.services;

import com.imo.backend.contexts.identity.User;

public interface SendForgetPasswordCodeService {
  User execute(String email);
}
