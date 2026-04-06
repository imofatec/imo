package com.imo.backend.contexts.identity.actions.impl;

import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.actions.UpdateUserAccessByIdAction;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateAccessUserByIdActionImpl implements UpdateUserAccessByIdAction {

  private final UserRepository userRepository;

  public UpdateAccessUserByIdActionImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(String id) {

    var foundUser =
        this.userRepository
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
