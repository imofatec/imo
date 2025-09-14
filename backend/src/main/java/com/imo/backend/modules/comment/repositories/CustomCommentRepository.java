package com.imo.backend.modules.comment.repositories;

import com.imo.backend.modules.comment.Comment;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomCommentRepository {
  List<Comment> findAllByLessonId(String lessonId);

  List<Comment> findAllByLessonId(String lessonId, Pageable pageable);
}
