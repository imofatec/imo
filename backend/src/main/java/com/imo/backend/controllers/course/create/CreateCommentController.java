package com.imo.backend.controllers.course.create;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.comments.Comment;
import com.imo.backend.models.comments.dto.CommentDTO;
import com.imo.backend.models.comments.service.create.CreateCommentServiceImpl;
import com.imo.backend.models.comments.service.create.interfaces.CreateCommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class CreateCommentController extends CourseController {

  private final CreateCommentService createCommentService;

  public CreateCommentController(CreateCommentService createCommentService) {
    this.createCommentService = createCommentService;
  }

  @Operation(summary = "Create Comment in lesson")
  @SecurityRequirement(name = "Authorization")
  @PostMapping("/comments/lessons/{lessonId}")
  public ResponseEntity<Comment> handle(
      HttpServletRequest request,
      @PathVariable String lessonId,
      @Valid @RequestBody CommentDTO commentDTO) {

    Comment savedComment = createCommentService.execute(request.getHeader("Authorization"),
        lessonId,
        commentDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedComment);
  }

}
