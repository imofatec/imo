package com.imo.backend.controllers.course.get.lessons.comment;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.comments.dto.ConvertedCommentDto;
import com.imo.backend.models.comments.service.get.interfaces.GetAllCommentsService;
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
public class GetAllCommentsController extends CourseController {

  private final GetAllCommentsService getAllCommentsService;

  public GetAllCommentsController(GetAllCommentsService getAllCommentsService) {
    this.getAllCommentsService = getAllCommentsService;
  }

  @Operation(summary = "Get all comments by lessonId")
  @GetMapping("/comments/{lessonId}")
  public ResponseEntity<List<ConvertedCommentDto>> handle(
      @PathVariable String lessonId,
      @RequestParam(required = false) Integer page,
      @Parameter(description = "Size of each page", example = "10")
      @RequestParam(required = false) Integer size) {
    var comments = (page != null && size != null)
        ? getAllCommentsService.execute(lessonId, page, size)
        : getAllCommentsService.execute(lessonId);

    return new ResponseEntity<>(comments, HttpStatus.OK);
  }
}
