package com.imo.backend.contexts.journey_tracking.controllers;

import com.imo.backend.contexts.common.http.dtos.PaginatedResponseDTO;
import com.imo.backend.contexts.journey_tracking.controllers.dtos.ProgressDetailsDTO;
import com.imo.backend.contexts.journey_tracking.repositories.ProgressRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetProgressByUserIdController extends ProgressController {
  private final ProgressRepository progressRepository;

  public GetProgressByUserIdController(ProgressRepository progressRepository) {
    this.progressRepository = progressRepository;
  }

  @Operation(summary = "Get progress details by logged user")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Progresso obtido com sucesso",
            content = @Content(schema = @Schema(implementation = PaginatedResponseDTO.class))),
        @ApiResponse(
            responseCode = "401",
            description = "Não autenticado",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @GetMapping("/details")
  public ResponseEntity<PaginatedResponseDTO<ProgressDetailsDTO>> handle(
      @Parameter(description = "Page number to retrieve", example = "0") @RequestParam Integer page,
      @Parameter(description = "Size of each page", example = "10") @RequestParam Integer size) {
    var userId = SecurityContextHolder.getContext().getAuthentication().getName();
    var progressDetailsList =
        this.progressRepository.findAllProgressDetailsByUserId(userId, page, size);
    long totalItems = this.progressRepository.countAllProgressDetailsByUserId(userId);

    var items = progressDetailsList.stream().map(ProgressDetailsDTO::fromProgressDetails).toList();

    return ResponseEntity.ok(PaginatedResponseDTO.from(items, page, size, totalItems));
  }
}
