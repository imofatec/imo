package com.imo.backend.modules.comment.guards;

import com.imo.backend.modules.comment.Comment;

import java.util.List;


public interface GetCommentsByLessonIdGuard {
  List<Comment> execute(String lessonId);

  List<Comment> execute(String lessonId, Integer page, Integer pageSize);
}
