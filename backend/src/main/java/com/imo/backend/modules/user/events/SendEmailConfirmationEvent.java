package com.imo.backend.modules.user.events;

import com.imo.backend.modules.user.http.dtos.UserDTO;

public record SendEmailConfirmationEvent(
    UserDTO user
) {
}
