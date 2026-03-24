package com.imo.backend.contexts.identity.user.events;

public record SendEmailConfirmationEvent(
    String email,
    String name
) {
}
