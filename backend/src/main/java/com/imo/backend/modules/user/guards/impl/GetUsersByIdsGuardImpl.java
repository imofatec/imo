package com.imo.backend.modules.user.guards.impl;

import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.guards.GetUsersByIdsGuard;
import com.imo.backend.modules.user.repositories.UserRepository;
import com.imo.backend.utils.ValidateObjectId;
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
