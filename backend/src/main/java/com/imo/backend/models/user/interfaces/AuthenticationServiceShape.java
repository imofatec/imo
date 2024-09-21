package com.imo.backend.models.user.interfaces;

import com.imo.backend.models.user.dtos.LoginRequest;
import com.imo.backend.models.user.dtos.LoginResponse;

public interface AuthenticationServiceShape {
    LoginResponse execute(LoginRequest loginRequest);
}
