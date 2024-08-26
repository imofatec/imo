package com.imo.backend.models.user.service;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.UserNotFoundException;
import com.imo.backend.exceptions.custom.WrongPasswordException;
import com.imo.backend.models.dto.LoginRequest;
import com.imo.backend.models.dto.LoginResponse;
import com.imo.backend.models.user.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(
            TokenService tokenService,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public LoginResponse execute(LoginRequest loginRequest) {
        var user = userRepository.findByEmail(loginRequest.getEmail());

        if (user.isEmpty()) {
            throw new UserNotFoundException();
        }

        var isMatch = passwordEncoder.matches(loginRequest.getPassword(),user.get().getPassword());
        if (!isMatch) {
            throw new WrongPasswordException();
        }

        return tokenService.generateToken(user.get().getUsername(), user.get().getId());
    }
}
