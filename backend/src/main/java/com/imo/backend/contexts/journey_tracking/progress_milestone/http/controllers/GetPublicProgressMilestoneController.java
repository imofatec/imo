package com.imo.backend.contexts.journey_tracking.progress_milestone.http.controllers;

import com.imo.backend.contexts.common.ApplicationUrlHelper;
import com.imo.backend.contexts.journey_tracking.progress_milestone.http.dtos.ProgressMilestoneDTO;
import com.imo.backend.contexts.journey_tracking.progress_milestone.usecases.GetPublicProgressMilestoneUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetPublicProgressMilestoneController extends ProgressMilestoneController {

  private final GetPublicProgressMilestoneUseCase getPublicProgressMilestoneUseCase;
  private final ApplicationUrlHelper applicationUrlHelper;

  public GetPublicProgressMilestoneController(
      GetPublicProgressMilestoneUseCase getPublicProgressMilestoneUseCase,
      ApplicationUrlHelper applicationUrlHelper) {
    this.getPublicProgressMilestoneUseCase = getPublicProgressMilestoneUseCase;
    this.applicationUrlHelper = applicationUrlHelper;
  }

  @Operation(summary = "Get public progress milestone by public code")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Marco de progresso encontrado",
            content = @Content(schema = @Schema(implementation = ProgressMilestoneDTO.class))),
        @ApiResponse(
            responseCode = "404",
            description = "Marco de progresso não encontrado",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @GetMapping("/public/{publicCode}")
  public ResponseEntity<ProgressMilestoneDTO> handle(@PathVariable String publicCode) {
    String baseUrl = this.applicationUrlHelper.getBackendBaseUrl();
    var milestone = this.getPublicProgressMilestoneUseCase.execute(publicCode);
    return ResponseEntity.ok(ProgressMilestoneDTO.fromProgressMilestone(milestone, baseUrl));
  }
}
