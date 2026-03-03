package com.imo.backend.contexts.identity.guards.impl;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.guards.GetUsersByIdsGuard;
import com.imo.backend.contexts.identity.repositories.UserRepository;
import com.imo.backend.contexts.common.ValidateObjectId;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUsersByIdsGuardImpl implements GetUsersByIdsGuard {
  private final UserRepository userRepository;

  public GetUsersByIdsGuardImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public List<User> execute(List<String> ids) {
    ids.forEach(ValidateObjectId::execute);
    return this.userRepository.findByIds(ids);
  }
}
