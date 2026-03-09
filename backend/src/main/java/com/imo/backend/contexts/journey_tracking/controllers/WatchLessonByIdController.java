package com.imo.backend.contexts.journey_tracking.controllers;

import com.imo.backend.contexts.journey_tracking.controllers.dtos.ProgressDTO;
import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.journey_tracking.use_cases.WatchLessonByIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WatchLessonByIdController extends ProgressController {
  private final WatchLessonByIdUseCase watchLessonByIdUseCase;

  public WatchLessonByIdController(
      WatchLessonByIdUseCase watchLessonByIdUseCase
  ) {
    this.watchLessonByIdUseCase = watchLessonByIdUseCase;
  }

  @Operation(summary = "Watch a lesson")
  @SecurityRequirement(name = "Authorization")
  @PutMapping("/{lessonId}")
  public ResponseEntity<ProgressDTO> handle(
      @PathVariable
      String lessonId
  ) {
    MongoDB.validateObjectId(lessonId);
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    var updatedProgress = this.watchLessonByIdUseCase.execute(lessonId, userId);
    return ResponseEntity.ok(updatedProgress);
  }
}
