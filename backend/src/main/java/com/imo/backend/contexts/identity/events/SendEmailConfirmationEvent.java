package com.imo.backend.contexts.identity.events;

import com.imo.backend.contexts.identity.http.dtos.UserDTO;

public record SendEmailConfirmationEvent(
    UserDTO user
) {
}
