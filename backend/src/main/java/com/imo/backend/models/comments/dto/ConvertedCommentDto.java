package com.imo.backend.models.comments.dto;

import java.time.LocalDateTime;

public record ConvertedCommentDto(
    String id, String userId, String username,
    String comment, String parentId,
    LocalDateTime createdAt, LocalDateTime updatedAt) {
}
