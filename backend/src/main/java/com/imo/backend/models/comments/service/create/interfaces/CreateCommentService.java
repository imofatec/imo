package com.imo.backend.models.comments.service.create.interfaces;

import com.imo.backend.models.comments.dto.CommentDTO;
import com.imo.backend.models.comments.dto.ConvertedCommentDto;

public interface CreateCommentService {
  ConvertedCommentDto execute(String token, String lessonId, CommentDTO comment);
}
