package com.imo.backend.contexts.social.comment.repositories;

import com.imo.backend.contexts.social.comment.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CustomCommentRepository {
  List<Comment> findAllByLessonId(String lessonId);

  List<Comment> findAllByLessonId(String lessonId, Pageable pageable);

  long countByLessonId(String lessonId);
}
