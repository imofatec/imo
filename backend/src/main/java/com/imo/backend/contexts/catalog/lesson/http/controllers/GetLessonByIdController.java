package com.imo.backend.contexts.catalog.lesson.http.controllers;

import com.imo.backend.contexts.catalog.lesson.http.dtos.LessonDTO;
import com.imo.backend.contexts.catalog.lesson.repositories.LessonRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetLessonByIdController extends LessonController {

  private final LessonRepository lessonRepository;

  public GetLessonByIdController(LessonRepository lessonRepository) {
    this.lessonRepository = lessonRepository;
  }

  @Operation(summary = "Get lesson by id")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Aula encontrada", content = @Content(schema = @Schema(implementation = LessonDTO.class))),
      @ApiResponse(responseCode = "401", description = "Não autenticado", content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class))),
      @ApiResponse(responseCode = "404", description = "Aula não encontrada", content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
  })
  @GetMapping("/{id}")
  public ResponseEntity<LessonDTO> execute(
      @PathVariable
      String id
  ) {
    var lesson = this.lessonRepository.findByIdOrThrow(id);
    return ResponseEntity.ok(LessonDTO.fromEntity(lesson));
  }
}
