package com.imo.backend.contexts.identity.events;

public record ForgetPasswordEvent(
    String userId,
    String email,
    String code
) {
}
