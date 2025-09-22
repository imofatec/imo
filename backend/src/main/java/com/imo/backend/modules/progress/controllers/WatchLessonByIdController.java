package com.imo.backend.modules.progress.controllers;

import com.imo.backend.modules.progress.controllers.dtos.ProgressDTO;
import com.imo.backend.modules.progress.orchestrators.WatchLessonByIdOrchestrator;
import com.imo.backend.utils.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WatchLessonByIdController extends ProgressController {
  private final WatchLessonByIdOrchestrator watchLessonByIdOrchestrator;

  public WatchLessonByIdController(
      WatchLessonByIdOrchestrator watchLessonByIdOrchestrator
  ) {
    this.watchLessonByIdOrchestrator = watchLessonByIdOrchestrator;
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
    var updatedProgress = this.watchLessonByIdOrchestrator.execute(lessonId, userId);
    return ResponseEntity.ok(updatedProgress);
  }
}
