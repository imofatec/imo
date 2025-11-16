package com.imo.backend.modules.user.events;

public record ForgetPasswordEvent(
    String userId,
    String email,
    String code
) {
}
