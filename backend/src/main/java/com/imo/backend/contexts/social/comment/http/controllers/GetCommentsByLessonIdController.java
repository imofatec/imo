package com.imo.backend.contexts.social.comment.http.controllers;

import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.common.Pageable;
import com.imo.backend.contexts.social.comment.http.dtos.CommentDTO;
import com.imo.backend.contexts.social.comment.repositories.CommentRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCommentsByLessonIdController extends CommentController {

  private final CommentRepository commentRepository;

  public GetCommentsByLessonIdController(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  @Operation(summary = "Get all comments by lessonId")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Comentários encontrados",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = CommentDTO.class))))
      })
  @GetMapping("/{lessonId}")
  public ResponseEntity<List<CommentDTO>> handle(
      @PathVariable String lessonId,
      @RequestParam(required = false) Integer page,
      @Parameter(description = "Size of each page", example = "10") @RequestParam(required = false)
          Integer size) {
    MongoDB.validateObjectId(lessonId);
    var comments =
        (page != null && size != null)
            ? this.commentRepository.findAllByLessonId(lessonId, Pageable.fromPageSize(page, size))
            : this.commentRepository.findAllByLessonId(lessonId);

    var commentsDTO = comments.stream().map(CommentDTO::fromEntity).toList();

    return new ResponseEntity<>(commentsDTO, HttpStatus.OK);
  }
}
