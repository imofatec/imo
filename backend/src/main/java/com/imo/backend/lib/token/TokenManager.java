package com.imo.backend.lib.token;

import com.imo.backend.contexts.identity.http.dtos.auth.LoginResponseDTO;

public interface TokenManager {
  LoginResponseDTO generateToken(String userId);

  String getUserId(String token);
}
