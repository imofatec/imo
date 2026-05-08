package com.imo.backend.contexts.identity.user.lib;

import com.imo.backend.contexts.identity.user.http.dtos.auth.LoginResponseDTO;

public interface TokenManager {
  LoginResponseDTO generateToken(String userId);

  String getUserId(String token);
}
