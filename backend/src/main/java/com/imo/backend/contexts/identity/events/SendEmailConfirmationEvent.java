package com.imo.backend.contexts.identity.events;

public record SendEmailConfirmationEvent(
    String email,
    String name
) {
}
