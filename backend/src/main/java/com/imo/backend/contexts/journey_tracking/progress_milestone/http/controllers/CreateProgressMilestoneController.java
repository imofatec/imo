package com.imo.backend.contexts.journey_tracking.progress_milestone.http.controllers;

import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.common.exceptions.ErrorResponseDto;
import com.imo.backend.contexts.journey_tracking.progress_milestone.http.dtos.ProgressMilestoneDTO;
import com.imo.backend.contexts.journey_tracking.progress_milestone.usecases.CreateProgressMilestoneUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class CreateProgressMilestoneController extends ProgressMilestoneController {
  private final CreateProgressMilestoneUseCase createProgressMilestoneUseCase;

  public CreateProgressMilestoneController(
      CreateProgressMilestoneUseCase createProgressMilestoneUseCase) {
    this.createProgressMilestoneUseCase = createProgressMilestoneUseCase;
  }

  @Operation(summary = "Create or refresh a progress milestone by course id")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Marco de progresso criado com sucesso",
            content = @Content(schema = @Schema(implementation = ProgressMilestoneDTO.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Não autenticado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(
            responseCode = "403",
            description = "Curso não finalizado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Progresso não encontrado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
      })
  @PutMapping("/course/{courseId}")
  public ResponseEntity<ProgressMilestoneDTO> handle(@PathVariable String courseId) {
    MongoDB.validateObjectId(courseId);
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();

    var milestone = this.createProgressMilestoneUseCase.execute(userId, courseId);

    return ResponseEntity.ok(ProgressMilestoneDTO.fromProgressMilestone(milestone, baseUrl));
  }
}
