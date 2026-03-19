package com.imo.backend.contexts.social.comment.http.controllers;

import com.imo.backend.contexts.social.comment.Comment;
import com.imo.backend.contexts.social.comment.http.dtos.CommentDTO;
import com.imo.backend.contexts.social.comment.http.dtos.CreateCommentRequest;
import com.imo.backend.contexts.social.comment.actions.CreateCommentAction;
import com.imo.backend.contexts.social.comment.commands.CreateCommentCommand;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateCommentController extends CommentController {

  private final CreateCommentAction createCommentAction;

  public CreateCommentController(CreateCommentAction createCommentAction) {
    this.createCommentAction = createCommentAction;
  }

  @Operation(summary = "Add comment in a lesson")
  @SecurityRequirement(name = "Authorization")
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

    Comment newComment = this.createCommentAction.execute(
        new CreateCommentCommand(createCommentRequest.content(), createCommentRequest.parentId()),
        userId,
        lessonId
    );

    return ResponseEntity.status(HttpStatus.CREATED).body(CommentDTO.fromEntity(newComment));
  }

}
