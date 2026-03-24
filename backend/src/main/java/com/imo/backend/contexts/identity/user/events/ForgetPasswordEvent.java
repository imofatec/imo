package com.imo.backend.contexts.identity.user.events;

public record ForgetPasswordEvent(
    String userId,
    String name,
    String email,
    String code
) {
}
