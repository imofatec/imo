package com.imo.backend.models.user.services.update;

import org.springframework.stereotype.Service;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.user.User;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.repositories.UserRepository;
import com.imo.backend.models.user.services.update.interfaces.IUpdateAccessUserService;

@Service
public class UpdateAccessUserService implements IUpdateAccessUserService{

private final UserRepository userRepository;

private final TokenService tokenService;

public UpdateAccessUserService(
    UserRepository userRepository,
    TokenService tokenService
) {
    this.userRepository = userRepository;
    this.tokenService = tokenService;
}

@Override
public NoPasswordUser execute(String token){
    String userId  = this.tokenService.getSub(token).get("id");

    this.userRepository.updateUserAccessById(userId, true);

    User user = this.userRepository.findById(userId)
    .orElseThrow(()-> new NotFoundException("Usuário não encontrado"));

    Boolean isActivated = user.getIsConfirmed();

    if(!isActivated) {
        throw new Error("Não foi possivel atualizar este usuário");
    }

    return NoPasswordUser.fromUser(user);
}
}
