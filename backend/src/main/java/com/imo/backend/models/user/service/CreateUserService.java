package com.imo.backend.models.user.service;

import com.imo.backend.exceptions.custom.EmailConflitException;
import com.imo.backend.exceptions.custom.UsernameConflitException;
import com.imo.backend.exceptions.custom.PasswordNotMatchException;
import com.imo.backend.models.user.dtos.CreateUserDto;
import com.imo.backend.models.user.User;
import com.imo.backend.models.user.UserRepository;
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

    public void execute(CreateUserDto createUserDto) {
        var username = userRepository.findByUsername(createUserDto.getUsername());
        if (username.isPresent()) {
            throw new UsernameConflitException();
        }

        var userEmail = userRepository.findByEmail(createUserDto.getEmail());
        if (userEmail.isPresent()) {
            throw new EmailConflitException();
        }

        if (!createUserDto.getConfPassword().matches(createUserDto.getPassword())) {
            throw new PasswordNotMatchException();
        }
        User newUser = new User(createUserDto.getUsername(),
                createUserDto.getEmail(),
                createUserDto.getPassword());

        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        userRepository.save(newUser);
    }
}
