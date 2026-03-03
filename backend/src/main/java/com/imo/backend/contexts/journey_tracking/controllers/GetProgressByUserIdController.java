package com.imo.backend.contexts.journey_tracking.controllers;

import com.imo.backend.contexts.journey_tracking.ProgressDetails;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetProgressByUserIdController extends ProgressController {
  private final ProgressRepository progressRepository;

  public GetProgressByUserIdController(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  @Operation(summary = "Get progress details by logged user")
  @SecurityRequirement(name = "Authorization")
  @GetMapping("/details")
  public ResponseEntity<List<ProgressDetails>> handle(
      @Parameter(description = "Page number to retrieve", example = "0", required = false)
      @RequestParam(required = false)
      Integer page,
      @Parameter(description = "Size of each page", example = "10", required = false)
      @RequestParam(required = false)
      Integer size
  ) {
    var userId = SecurityContextHolder.getContext().getAuthentication().getName();
    return ResponseEntity.ok((page == null || size == null)
        ? this.progressRepository.findAllProgressDetailsByUserId(userId)
        : this.progressRepository.findAllProgressDetailsByUserId(userId, page, size));
  }
}
