package com.imo.backend.contexts.identity.events;

public record ForgetPasswordMessagePayload(
    String outboxId, String userId, String email, String code) {}
