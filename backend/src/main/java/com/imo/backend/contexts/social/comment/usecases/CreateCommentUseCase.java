package com.imo.backend.contexts.social.comment.usecases;

import org.springframework.stereotype.Service;

import com.imo.backend.contexts.social.comment.Comment;
import com.imo.backend.contexts.social.comment.commands.CreateCommentCommand;
import com.imo.backend.contexts.social.comment.repositories.CommentRepository;

@Service
public class CreateCommentUseCase {
  private final CommentRepository commentRepository;

  public CreateCommentUseCase(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  public Comment execute(CreateCommentCommand createCommentCommand, String userId, String lessonId) {
    Comment comment = new Comment(
        userId,
        lessonId,
        createCommentCommand.parentId(),
        createCommentCommand.content()
    );

    return this.commentRepository.save(comment);
  }
}
