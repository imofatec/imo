package com.imo.backend.models.comments.service.create.interfaces;

import com.imo.backend.models.comments.Comment;
import com.imo.backend.models.comments.dto.CommentDTO;

public interface CreateCommentService {
  Comment execute(String token, String lessonId, CommentDTO comment);
}
