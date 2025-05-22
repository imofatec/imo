package com.imo.backend.models.comments.service.get.interfaces;

import com.imo.backend.models.comments.dto.ConvertedCommentDto;

import java.util.List;


public interface GetAllCommentsService {
  List<ConvertedCommentDto> execute(String lessonId);

  List<ConvertedCommentDto> execute(String lessonId, Integer page, Integer size);
}
