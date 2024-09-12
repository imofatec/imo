package com.imo.backend.models.user.service;

import com.imo.backend.exceptions.custom.ConflictException;
import com.imo.backend.exceptions.custom.BadRequestException;
import com.imo.backend.models.user.dtos.RegisterUserRequest;
import com.imo.backend.models.user.User;
import com.imo.backend.models.user.UserRepository;
import com.imo.backend.models.user.dtos.RegisterUserResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public CreateUserService(UserRepository userRepository,
                             PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterUserResponse execute(RegisterUserRequest registerUserRequest) {
        var username = userRepository.findByUsername(registerUserRequest.getUsername());
        if (username.isPresent()) {
            throw new ConflictException("O username já existe");
        }

        var userEmail = userRepository.findByEmail(registerUserRequest.getEmail());
        if (userEmail.isPresent()) {
            throw new ConflictException("O email já existe");
        }

        if (!registerUserRequest.getConfPassword().matches(registerUserRequest.getPassword())) {
            throw new BadRequestException("As senhas não coincidem");
        }
        User user = new User(registerUserRequest.getUsername(),
                registerUserRequest.getEmail(),
                registerUserRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User newUser = userRepository.save(user);
        return new RegisterUserResponse(newUser.getUsername(), newUser.getEmail());
    }
}
