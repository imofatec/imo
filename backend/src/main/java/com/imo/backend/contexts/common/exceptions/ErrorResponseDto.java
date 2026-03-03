package com.imo.backend.contexts.common.exceptions;

public record ErrorResponseDto(
    String message,
    String description
) {
}
