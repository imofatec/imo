package com.imo.backend.contexts.identity.services;

import com.imo.backend.contexts.identity.http.dtos.auth.LoginRequestDTO;
import com.imo.backend.contexts.identity.http.dtos.auth.LoginResponseDTO;

public interface AuthenticateUserService {
  LoginResponseDTO execute(LoginRequestDTO loginRequestDTO);
}
