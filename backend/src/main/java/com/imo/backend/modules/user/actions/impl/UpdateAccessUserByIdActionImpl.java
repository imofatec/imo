package com.imo.backend.modules.user.actions.impl;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.actions.UpdateUserAccessByIdAction;
import com.imo.backend.modules.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateAccessUserByIdActionImpl implements UpdateUserAccessByIdAction {

  private final UserRepository userRepository;

  public UpdateAccessUserByIdActionImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(String id) {

    var foundUser = this.userRepository
        .findById(id)
        .orElseThrow(() -> new NotFoundException("Usuário não encontrado"));

    var user = this.userRepository.toggleAccessById(id, foundUser.getIsConfirmed());

    Boolean isActivated = user.getIsConfirmed();

    if (!isActivated) {
      throw new Error("Não foi possivel atualizar este usuário");
    }

    return user;
  }
}
