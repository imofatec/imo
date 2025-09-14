package com.imo.backend.modules.user.actions.inputs;

public record UpdateUserByIdInput(
    String email,
    String name,
    String password,
    String profilePicturePath
) {
}
