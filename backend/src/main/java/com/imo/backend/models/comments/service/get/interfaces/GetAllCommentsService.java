package com.imo.backend.models.comments.service.get.interfaces;

import com.imo.backend.models.comments.Comment;

import java.util.List;


public interface GetAllCommentsService {
  List<Comment> execute(String lessonId);
}
