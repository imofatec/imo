package com.imo.backend.contexts.journey_tracking.progress.controllers;

import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.journey_tracking.progress.controllers.dtos.ProgressDetailsDTO;
import com.imo.backend.contexts.journey_tracking.progress.repositories.ProgressRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Progresso obtido com sucesso",
            content = @Content(schema = @Schema(implementation = ProgressDetailsDTO.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Não autenticado",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto
                                    .class))),
        @ApiResponse(
            responseCode = "404",
            description = "Progresso não encontrado",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @GetMapping("/details/{courseId}")
  public ResponseEntity<ProgressDetailsDTO> handle(@PathVariable String courseId) {
    MongoDB.validateObjectId(courseId);
    var userId = SecurityContextHolder.getContext().getAuthentication().getName();
    var progressDetails = this.progressRepository.findProgressDetailsOrThrow(userId, courseId);
    return ResponseEntity.ok(ProgressDetailsDTO.fromProgressDetails(progressDetails));
  }
}
