package com.imo.backend.contexts.identity.actions.impl;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.actions.UpdateUserByIdAction;
import com.imo.backend.contexts.identity.actions.helpers.UserUpdater;
import com.imo.backend.contexts.identity.actions.inputs.UpdateUserByIdInput;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UpdateUserByIdActionImpl implements UpdateUserByIdAction {
  private final UserRepository userRepository;

  public UpdateUserByIdActionImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public User execute(String id, UpdateUserByIdInput input) {
    var foundUser = userRepository.findById(id).orElse(null);

    if (foundUser == null) {
      return null;
    }

    UserUpdater.apply(foundUser, input);

    return this.userRepository.save(foundUser);
  }
}
