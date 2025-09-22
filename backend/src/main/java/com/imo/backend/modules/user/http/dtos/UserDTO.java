package com.imo.backend.modules.user.http.dtos;

import com.imo.backend.modules.user.User;

public record UserDTO(
    String id,
    String name,
    String email,
    Boolean isConfirmed,
    String profilePicturePath
) {
  public static UserDTO fromUser(User user) {
    return (user == null) ? null : new UserDTO(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getIsConfirmed(),
        user.getProfilePicturePath() != null ? user.getProfilePicturePath() : ""
    );
  }
}
