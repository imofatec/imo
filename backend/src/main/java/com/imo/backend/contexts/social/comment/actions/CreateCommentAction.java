package com.imo.backend.contexts.social.comment.actions;

import com.imo.backend.contexts.social.comment.Comment;
import com.imo.backend.contexts.social.comment.commands.CreateCommentCommand;

public interface CreateCommentAction {
  Comment execute(CreateCommentCommand createCommentCommand, String userId, String lessonId);
}
