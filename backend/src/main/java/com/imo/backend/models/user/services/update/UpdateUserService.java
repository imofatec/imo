package com.imo.backend.models.user.services.update;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.strategy.update.UpdateByIdService;
import com.imo.backend.models.user.dtos.FieldsToUpdateUser;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.repositories.CustomUserRepositoryImpl;
import com.imo.backend.models.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UpdateUserService implements UpdateByIdService<FieldsToUpdateUser,NoPasswordUser> {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final CustomUserRepositoryImpl customUserRepository;

    public UpdateUserService(UserRepository userRepository, TokenService tokenService, CustomUserRepositoryImpl customUserRepository) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.customUserRepository = customUserRepository;
    }

    public NoPasswordUser execute(String notReturn,FieldsToUpdateUser fieldsToUpdateUser,String token){

        Map<String, String> subAsMap = tokenService.getSub(token);
        var userid = subAsMap.get("id");

        customUserRepository.updateUser(userid, fieldsToUpdateUser);

        var updatedUser = userRepository.findById(userid)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        return NoPasswordUser.fromUser(updatedUser);
    }
}
