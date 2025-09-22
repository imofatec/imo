package com.imo.backend.modules.comment.actions;

import com.imo.backend.modules.comment.Comment;
import com.imo.backend.modules.comment.commands.inputs.CreateCommentInput;

public interface CreateCommentAction {
  Comment execute(CreateCommentInput createCommentInput, String userId, String lessonId);
}
