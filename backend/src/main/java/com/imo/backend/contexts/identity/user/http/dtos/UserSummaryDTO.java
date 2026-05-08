package com.imo.backend.contexts.identity.user.http.dtos;

import com.imo.backend.contexts.identity.user.User;

public record UserSummaryDTO(String id, String name, String profilePicturePath) {
  public static UserSummaryDTO fromUser(User user) {
    return (user == null)
        ? null
        : new UserSummaryDTO(user.getId(), user.getName(), user.getProfilePicturePath());
  }
}
