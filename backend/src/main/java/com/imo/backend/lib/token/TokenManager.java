package com.imo.backend.lib.token;

import com.imo.backend.modules.user.http.dtos.auth.LoginResponseDTO;

public interface TokenManager {
  LoginResponseDTO generateToken(String userId);

  String getUserId(String token);
}
