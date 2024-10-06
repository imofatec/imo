package com.imo.backend.models.user.services;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.exceptions.custom.ForbiddenException;
import com.imo.backend.models.user.dtos.LoginRequest;
import com.imo.backend.models.user.dtos.LoginResponse;
import com.imo.backend.models.user.repositories.UserRepository;
import com.imo.backend.models.user.interfaces.AuthenticationServiceShape;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService implements AuthenticationServiceShape {

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
            throw new NotFoundException("Email não encontrado");
        }

        var isMatch = passwordEncoder.matches(loginRequest.getPassword(),user.get().getPassword());
        if (!isMatch) {
            throw new ForbiddenException("Senha inválida");
        }

        return tokenService.generateToken(user.get().getUsername(), user.get().getId());
    }
}
