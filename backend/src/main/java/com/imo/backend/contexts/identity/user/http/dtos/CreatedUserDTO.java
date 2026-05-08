package com.imo.backend.contexts.identity.user.http.dtos;

import com.imo.backend.contexts.identity.user.User;

public record CreatedUserDTO(String id, String name, Boolean isConfirmed) {
  public static CreatedUserDTO fromUser(User user) {
    return (user == null)
        ? null
        : new CreatedUserDTO(user.getId(), user.getName(), user.getIsConfirmed());
  }
}
