package com.imo.backend.contexts.identity.user.http.dtos;

import com.imo.backend.contexts.identity.user.User;

public record UserProfilePictureDTO(String id, String profilePicturePath) {
  public static UserProfilePictureDTO fromUser(User user) {
    return (user == null)
        ? null
        : new UserProfilePictureDTO(user.getId(), user.getProfilePicturePath());
  }
}
