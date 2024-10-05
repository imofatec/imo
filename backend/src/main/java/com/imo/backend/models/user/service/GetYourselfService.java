package com.imo.backend.models.user.service;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.strategy.read.GetOneByService;
import com.imo.backend.models.user.User;
import com.imo.backend.models.user.UserRepository;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GetYourselfService implements GetOneByService<NoPasswordUser> {

    private final TokenService tokenService;
    private final UserRepository userRepository;


    public GetYourselfService(TokenService tokenService, UserRepository userRepository) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    public NoPasswordUser execute(String token) {
        Map<String, String> subAsMap = tokenService.getSub(token);
        var userId = subAsMap.get("id");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found"));
        return new NoPasswordUser(user.getUsername(), user.getEmail());
    }
}
