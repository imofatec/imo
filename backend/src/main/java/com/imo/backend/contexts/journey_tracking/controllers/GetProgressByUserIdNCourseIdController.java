package com.imo.backend.contexts.journey_tracking.controllers;

import com.imo.backend.contexts.journey_tracking.ProgressDetails;
import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProgressByUserIdNCourseIdController extends ProgressController {
  private final ProgressRepository progressRepository;

  public GetProgressByUserIdNCourseIdController(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  @Operation(summary = "Get progress details of a course by logged user")
  @SecurityRequirement(name = "Authorization")
  @GetMapping("/details/{courseId}")
  public ResponseEntity<ProgressDetails> handle(
      @PathVariable
      String courseId
  ) {
    MongoDB.validateObjectId(courseId);
    var userId = SecurityContextHolder.getContext().getAuthentication().getName();
    return ResponseEntity.ok(this.progressRepository.findDetailsByUserIdAndCourseIdOrThrow(
        userId,
        courseId
    ));
  }
}
