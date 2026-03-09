package com.imo.backend.contexts.social.comment.use_cases;

import com.imo.backend.contexts.social.comment.Comment;
import com.imo.backend.contexts.social.comment.repositories.CommentRepository;
import com.imo.backend.contexts.social.comment.use_cases.commands.CreateCommentCommand;
import org.springframework.stereotype.Service;

@Service
public class CreateCommentUseCase {
  private final CommentRepository commentRepository;

  public CreateCommentUseCase(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  public Comment execute(CreateCommentCommand command, String userId, String lessonId) {
    Comment comment = new Comment(
        userId,
        lessonId,
        command.parentId(),
        command.content()
    );

    return this.commentRepository.save(comment);
  }
}
