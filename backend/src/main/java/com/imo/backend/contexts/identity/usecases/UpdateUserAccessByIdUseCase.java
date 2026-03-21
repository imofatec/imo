package com.imo.backend.contexts.identity.usecases;

import org.springframework.stereotype.Service;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.repositories.UserRepository;

@Service
public class UpdateUserAccessByIdUseCase {

    private final UserRepository userRepository;

    public UpdateUserAccessByIdUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User execute(String id) {
        var foundUser = this.userRepository
                .findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

        if (foundUser.getIsConfirmed()) {
            return foundUser;
        }

        var user = this.userRepository.toggleAccessById(id, foundUser.getIsConfirmed());

        Boolean isActivated = user.getIsConfirmed();

        if (!isActivated) {
            throw new Error("Não foi possivel atualizar este usuário");
        }
        return user;
    }
}
