package com.imo.backend.contexts.identity.user.http.dtos;

import com.imo.backend.contexts.identity.user.User;

public record UserConfirmationDTO(String id, Boolean isConfirmed) {
  public static UserConfirmationDTO fromUser(User user) {
    return (user == null) ? null : new UserConfirmationDTO(user.getId(), user.getIsConfirmed());
  }
}
