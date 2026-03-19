package com.imo.backend.contexts.social.comment.http.controllers;

import com.imo.backend.contexts.social.comment.Comment;
import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.common.Pageable;
import com.imo.backend.contexts.social.comment.repositories.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCommentsByLessonIdController extends CommentController {

  private final CommentRepository commentRepository;

  public GetCommentsByLessonIdController(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Operation(summary = "Get all comments by lessonId")
  @GetMapping("/{lessonId}")
  public ResponseEntity<List<Comment>> handle(
      @PathVariable
      String lessonId,
      @RequestParam(required = false)
      Integer page,
      @Parameter(description = "Size of each page", example = "10")
      @RequestParam(required = false)
      Integer size
  ) {
    MongoDB.validateObjectId(lessonId);
    var comments = (page != null && size != null)
        ? this.commentRepository.findAllByLessonId(lessonId, Pageable.fromPageSize(page, size))
        : this.commentRepository.findAllByLessonId(lessonId);

    return new ResponseEntity<>(comments, HttpStatus.OK);
  }
}
