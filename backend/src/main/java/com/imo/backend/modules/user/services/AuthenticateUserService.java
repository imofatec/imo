package com.imo.backend.modules.user.services;

import com.imo.backend.modules.user.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.modules.user.http.dtos.auth.LoginResponseDTO;

public interface AuthenticateUserService {
  LoginResponseDTO execute(LoginRequestDTO loginRequestDTO);
}
