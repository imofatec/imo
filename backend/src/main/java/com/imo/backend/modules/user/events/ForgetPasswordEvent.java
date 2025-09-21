package com.imo.backend.modules.user.events;

public record ForgetPasswordEvent(
    String userId,
    String userEmail,
    String checkCode
) {
}
