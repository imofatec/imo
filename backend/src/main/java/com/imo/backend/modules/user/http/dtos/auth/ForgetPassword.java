package com.imo.backend.modules.user.http.dtos.auth;

public record ForgetPassword(
    String userId,
    String email,
    String code
) {
}
