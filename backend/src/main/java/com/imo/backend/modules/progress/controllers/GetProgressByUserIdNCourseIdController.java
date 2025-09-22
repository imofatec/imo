package com.imo.backend.modules.progress.controllers;

import com.imo.backend.modules.progress.ProgressDetails;
import com.imo.backend.modules.progress.guards.GetProgressDetailsByUserIdAndCourseIdGuard;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProgressByUserIdNCourseIdController extends ProgressController {
  private final GetProgressDetailsByUserIdAndCourseIdGuard getProgressDetailsByUserIdAndCourseIdGuard;

  public GetProgressByUserIdNCourseIdController(GetProgressDetailsByUserIdAndCourseIdGuard getProgressDetailsByUserIdAndCourseIdGuard) {
    this.getProgressDetailsByUserIdAndCourseIdGuard = getProgressDetailsByUserIdAndCourseIdGuard;
  }

  @Operation(summary = "Get progress details of a course by logged user")
  @SecurityRequirement(name = "Authorization")
  @GetMapping("/details/{courseId}")
  public ResponseEntity<ProgressDetails> handle(
      @PathVariable
      String courseId
  ) {

    var userId = SecurityContextHolder.getContext().getAuthentication().getName();
    return ResponseEntity.ok(this.getProgressDetailsByUserIdAndCourseIdGuard.execute(
        userId,
        courseId
    ));
  }
}
