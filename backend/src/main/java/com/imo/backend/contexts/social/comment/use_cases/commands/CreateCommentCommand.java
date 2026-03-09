package com.imo.backend.contexts.social.comment.use_cases.commands;

public record CreateCommentCommand(
    String content,
    String parentId
) {
}
