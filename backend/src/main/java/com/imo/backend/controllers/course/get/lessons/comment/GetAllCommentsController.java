package com.imo.backend.controllers.course.get.lessons.comment;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.comments.Comment;
import com.imo.backend.models.comments.service.get.interfaces.GetAllCommentsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
  public ResponseEntity<List<Comment>> handle(
      @PathVariable String lessonId) {
    var comments = getAllCommentsService.execute(lessonId);
    return new ResponseEntity<>(comments, HttpStatus.OK);
  }
}
