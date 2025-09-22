package com.imo.backend.modules.comment.actions.impl;

import com.imo.backend.modules.comment.Comment;
import com.imo.backend.modules.comment.actions.CreateCommentAction;
import com.imo.backend.modules.comment.commands.inputs.CreateCommentInput;
import com.imo.backend.modules.comment.repositories.CommentRepository;
import org.springframework.stereotype.Service;

@Service
public class CreateCommentActionImpl implements CreateCommentAction {
  private final CommentRepository commentRepository;

  public CreateCommentActionImpl(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Override
  public Comment execute(CreateCommentInput createCommentInput, String userId, String lessonId) {
    Comment comment = new Comment(
        userId,
        lessonId,
        createCommentInput.parentId(),
        createCommentInput.content()
    );

    return this.commentRepository.save(comment);
  }
}
