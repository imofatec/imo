package com.imo.backend.contexts.social.comment.guards;

import com.imo.backend.contexts.social.comment.Comment;
import java.util.List;

public interface GetCommentsByLessonIdGuard {
  List<Comment> execute(String lessonId);

  List<Comment> execute(String lessonId, Integer page, Integer pageSize);
}
