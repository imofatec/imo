package com.imo.backend.contexts.recommendation.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDTO;
import com.imo.backend.contexts.common.exceptions.ErrorResponseDto;
import com.imo.backend.contexts.recommendation.usecases.GetMyRecommendationsUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetMyRecommendationsController extends RecommendationController {
  private final GetMyRecommendationsUseCase getMyRecommendationsUseCase;

  public GetMyRecommendationsController(GetMyRecommendationsUseCase getMyRecommendationsUseCase) {
    this.getMyRecommendationsUseCase = getMyRecommendationsUseCase;
  }

  @Operation(summary = "Get logged user recommendations")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Recomendações obtidas com sucesso",
            content =
                @Content(array = @ArraySchema(schema = @Schema(implementation = CourseDTO.class)))),
        @ApiResponse(
            responseCode = "401",
            description = "Não autenticado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
      })
  @GetMapping("/me")
  public ResponseEntity<List<CourseDTO>> handle() {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    List<CourseDTO> response = this.getMyRecommendationsUseCase.execute(userId);

    return ResponseEntity.ok(response);
  }
}
