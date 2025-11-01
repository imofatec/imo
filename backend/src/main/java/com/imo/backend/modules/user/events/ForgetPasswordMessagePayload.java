package com.imo.backend.modules.user.events;

public record ForgetPasswordMessagePayload(
    String outboxId,
    String userId,
    String email,
    String code
) {
}
