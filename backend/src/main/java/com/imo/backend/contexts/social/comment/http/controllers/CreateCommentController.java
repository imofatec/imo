package com.imo.backend.contexts.social.comment.http.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.social.comment.Comment;
import com.imo.backend.contexts.social.comment.commands.CreateCommentCommand;
import com.imo.backend.contexts.social.comment.http.dtos.CommentDTO;
import com.imo.backend.contexts.social.comment.http.dtos.CreateCommentRequest;
import com.imo.backend.contexts.social.comment.usecases.CreateCommentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RestController
public class CreateCommentController extends CommentController {

  private final CreateCommentUseCase createCommentUseCase;

  public CreateCommentController(CreateCommentUseCase createCommentUseCase) {
    this.createCommentUseCase = createCommentUseCase;
  }

  @Operation(summary = "Add comment in a lesson")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Comentário criado com sucesso",
          content = @Content(schema = @Schema(implementation = CommentDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class))),
      @ApiResponse(responseCode = "401", description = "Não autenticado",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class))),
      @ApiResponse(responseCode = "404", description = "Aula não encontrada",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
  })
  @PostMapping("/{lessonId}")
  public ResponseEntity<CommentDTO> handle(
      @PathVariable
      String lessonId,
      @Valid
      @RequestBody
      CreateCommentRequest createCommentRequest
  ) {
    MongoDB.validateObjectId(lessonId);
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    Comment newComment = this.createCommentUseCase.execute(
        new CreateCommentCommand(createCommentRequest.content(), createCommentRequest.parentId()),
        userId,
        lessonId
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(CommentDTO.fromEntity(newComment));
  }

}
