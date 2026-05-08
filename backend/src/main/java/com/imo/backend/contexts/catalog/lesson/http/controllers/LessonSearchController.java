package com.imo.backend.contexts.catalog.lesson.http.controllers;

import com.imo.backend.contexts.catalog.lesson.http.dtos.LessonDTO;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonSearchParams;
import com.imo.backend.contexts.common.CombineWith;
import com.imo.backend.contexts.common.MatchType;
import com.imo.backend.contexts.common.http.dtos.PaginatedResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LessonSearchController extends LessonController {
  private final LessonRepository lessonRepository;

  public LessonSearchController(LessonRepository lessonRepository) {
    this.lessonRepository = lessonRepository;
  }

  @Operation(summary = "Search lesson")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Aulas encontradas",
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
  @GetMapping("/search")
  public ResponseEntity<PaginatedResponseDTO<LessonDTO>> handle(
      @Parameter(description = "Query params to search", example = "slugCategory=dev-web")
          @ParameterObject
          LessonSearchParams lessonSearchParams,
      @Parameter(description = "Query type of match", example = "PERFECT")
          @RequestParam(defaultValue = "PERFECT")
          MatchType matchType,
      @Parameter(description = "Query params to search", example = "AND")
          @RequestParam(defaultValue = "AND")
          CombineWith combineWith,
      @Parameter(description = "Page number to retrieve", example = "0") @RequestParam Integer page,
      @Parameter(description = "Size of each page", example = "10") @RequestParam Integer size) {
    var lessons =
        this.lessonRepository.search(lessonSearchParams, page, size, matchType, combineWith);
    long totalItems = this.lessonRepository.countSearch(lessonSearchParams, matchType, combineWith);
    var items = lessons.stream().map(LessonDTO::fromEntity).toList();

    return ResponseEntity.ok(PaginatedResponseDTO.from(items, page, size, totalItems));
  }
}
