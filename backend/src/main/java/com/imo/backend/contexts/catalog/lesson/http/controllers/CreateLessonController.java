package com.imo.backend.contexts.catalog.lesson.http.controllers;

import com.imo.backend.contexts.catalog.course.http.middlewares.ValidateUserCourseAccessService;
import com.imo.backend.contexts.catalog.lesson.http.dtos.CreateLessonRequest;
import com.imo.backend.contexts.catalog.lesson.http.dtos.LessonDTO;
import com.imo.backend.contexts.catalog.lesson.usecases.CreateLessonUseCase;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CreateLessonController extends LessonController {
  private final ValidateUserCourseAccessService validateUserCourseAccessService;

  private final CreateLessonUseCase useCase;

  public CreateLessonController(
      ValidateUserCourseAccessService validateUserCourseAccessService,
      CreateLessonUseCase useCase
  ) {
    this.validateUserCourseAccessService = validateUserCourseAccessService;
    this.useCase = useCase;
  }

  @Operation(summary = "Add new lesson in a course")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "201", description = "Aula criada com sucesso",
          content = @Content(schema = @Schema(implementation = LessonDTO.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class))),
      @ApiResponse(responseCode = "401", description = "Não autenticado",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class))),
      @ApiResponse(responseCode = "403", description = "Sem permissão para adicionar aula neste curso",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class))),
      @ApiResponse(responseCode = "404", description = "Curso não encontrado",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class))),
      @ApiResponse(responseCode = "409", description = "Título ou link já existem no curso",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
  })
  @PostMapping("/{courseId}")
  public ResponseEntity<LessonDTO> handle(
      HttpServletRequest request,
      @PathVariable
      String courseId,
      @Valid
      @RequestBody
      CreateLessonRequest dto
  ) {
    MongoDB.validateObjectId(courseId);
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();
    this.validateUserCourseAccessService.execute(userId, courseId);

    var newLesson = this.useCase.execute(dto.toCommand(), courseId);

    return new ResponseEntity<>(LessonDTO.fromEntity(newLesson), HttpStatus.CREATED);
  }
}
