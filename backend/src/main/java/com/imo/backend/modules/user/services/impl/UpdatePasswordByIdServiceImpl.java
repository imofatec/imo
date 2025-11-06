package com.imo.backend.modules.user.services.impl;

import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.actions.UpdateUserByIdAction;
import com.imo.backend.modules.user.actions.inputs.UpdateUserByIdInput;
import com.imo.backend.modules.user.services.UpdatePasswordByIdService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UpdatePasswordByIdServiceImpl implements UpdatePasswordByIdService {
  private final UpdateUserByIdAction updateUserByIdAction;

  private final PasswordEncoder passwordEncoder;

  public UpdatePasswordByIdServiceImpl(
      UpdateUserByIdAction updateUserByIdAction,
      PasswordEncoder passwordEncoder
  ) {
    this.updateUserByIdAction = updateUserByIdAction;
    this.passwordEncoder = passwordEncoder;
  }

  public User execute(String userId, String newPassword) {
    var hashedPassword = passwordEncoder.encode(newPassword);

    return this.updateUserByIdAction.execute(
        userId,
        new UpdateUserByIdInput(null, null, hashedPassword, null, null, null, null, null, null)
    );
  }
}
