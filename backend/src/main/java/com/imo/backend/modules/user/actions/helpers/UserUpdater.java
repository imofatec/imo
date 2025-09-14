package com.imo.backend.modules.user.actions.helpers;

import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.actions.inputs.UpdateUserByIdInput;

public class UserUpdater {
  public static void apply(User user, UpdateUserByIdInput dto) {
    if (dto.name() != null && !dto.name().isEmpty()) {
      user.setName(dto.name());
    }

    if (dto.email() != null && !dto.email().isEmpty()) {
      user.setEmail(dto.email());
    }

    if (dto.password() != null && !dto.password().isEmpty()) {
      user.setPassword(dto.password());
    }

    if (dto.profilePicturePath() != null && !dto.profilePicturePath().isEmpty()) {
      user.setProfilePicturePath(dto.profilePicturePath());
    }
  }
}
