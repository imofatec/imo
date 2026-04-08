package com.imo.backend.contexts.social.comment.http.dtos;

import com.imo.backend.contexts.social.comment.Comment;

public record CommentDTO(
    String id, String userId, String lessonId, String parentId, String content) {
  public static CommentDTO fromEntity(Comment comment) {
    return new CommentDTO(
        comment.getId(),
        comment.getUserId(),
        comment.getLessonId(),
        comment.getParentId(),
        comment.getContent());
  }
}
