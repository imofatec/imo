package com.imo.backend.contexts.social.comment.repositories;

import com.imo.backend.contexts.social.comment.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomCommentRepository {
  List<Comment> findAllByLessonId(String lessonId);

  List<Comment> findAllByLessonId(String lessonId, Pageable pageable);
}
