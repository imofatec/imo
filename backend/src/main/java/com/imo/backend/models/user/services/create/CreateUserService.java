package com.imo.backend.models.user.services.create;

import com.imo.backend.exceptions.custom.ConflictException;
import com.imo.backend.exceptions.custom.BadRequestException;
import com.imo.backend.models.strategy.create.CreateService;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.dtos.RegisterUserRequest;
import com.imo.backend.models.user.User;
import com.imo.backend.models.user.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserService implements CreateService<RegisterUserRequest, NoPasswordUser> {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public CreateUserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public NoPasswordUser execute(RegisterUserRequest registerUserRequest) {
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

        User user = new User(
                registerUserRequest.getUsername(),
                registerUserRequest.getEmail(),
                registerUserRequest.getPassword());

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User newUser = userRepository.save(user);

        return NoPasswordUser.fromUser(newUser);
    }
}
