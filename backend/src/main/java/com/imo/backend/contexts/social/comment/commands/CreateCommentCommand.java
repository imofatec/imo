package com.imo.backend.contexts.social.comment.commands;

public record CreateCommentCommand(String content, String parentId) {}
