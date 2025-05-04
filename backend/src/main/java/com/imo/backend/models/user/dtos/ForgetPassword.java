package com.imo.backend.models.user.dtos;

public record ForgetPassword(String userId, String email, String code) {
}
