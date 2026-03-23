package com.imo.backend.contexts.identity.lib;

import com.imo.backend.contexts.identity.http.dtos.auth.LoginResponseDTO;

public interface TokenManager {
  LoginResponseDTO generateToken(String userId);

  String getUserId(String token);
}
