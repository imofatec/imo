package com.imo.backend.contexts.social.comment.actions;

import com.imo.backend.contexts.social.comment.Comment;
import com.imo.backend.contexts.social.comment.actions.inputs.CreateCommentInput;

public interface CreateCommentAction {
  Comment execute(CreateCommentInput createCommentInput, String userId, String lessonId);
}
