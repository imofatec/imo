package com.imo.backend.contexts.social.comment.guards.impl;

import com.imo.backend.contexts.social.comment.Comment;
import com.imo.backend.contexts.social.comment.guards.GetCommentsByLessonIdGuard;
import com.imo.backend.contexts.social.comment.repositories.CommentRepository;
import com.imo.backend.contexts.common.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCommentsByLessonIdGuardImpl implements GetCommentsByLessonIdGuard {

  private final CommentRepository commentRepository;

  public GetCommentsByLessonIdGuardImpl(
      CommentRepository commentRepository
  ) {
    this.commentRepository = commentRepository;
  }

  @Override
  public List<Comment> execute(String lessonId) {
    return this.commentRepository.findAllByLessonId(lessonId);
  }

  public List<Comment> execute(String lessonId, Integer page, Integer pageSize) {
    return this.commentRepository.findAllByLessonId(
        lessonId,
        Pageable.fromPageSize(page, pageSize)
    );
  }
}
