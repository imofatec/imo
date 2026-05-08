package com.imo.backend.contexts.catalog.lesson.http.controllers;

import com.imo.backend.contexts.catalog.course.http.middlewares.ValidateUserCourseAccessService;
import com.imo.backend.contexts.catalog.course.repositories.CourseRepository;
import com.imo.backend.contexts.catalog.lesson.http.dtos.LessonDTO;
import com.imo.backend.contexts.catalog.lesson.usecases.DeleteLessonByIdUseCase;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteLessonByIdController extends LessonController {

  private final DeleteLessonByIdUseCase useCase;

  private final ValidateUserCourseAccessService validateUserCourseAccessService;

  private final CourseRepository courseRepository;

  public DeleteLessonByIdController(
      DeleteLessonByIdUseCase useCase,
      ValidateUserCourseAccessService validateUserCourseAccessService,
      CourseRepository courseRepository) {
    this.useCase = useCase;
    this.validateUserCourseAccessService = validateUserCourseAccessService;
    this.courseRepository = courseRepository;
  }

  @Operation(summary = "Delete lesson by id")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Aula deletada com sucesso",
            content = @Content(schema = @Schema(implementation = LessonDTO.class))),
        @ApiResponse(responseCode = "204", description = "Aula não encontrada"),
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
            responseCode = "403",
            description = "Sem permissão para deletar aula neste curso",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto
                                    .class))),
        @ApiResponse(
            responseCode = "404",
            description = "Curso ou aula não encontrados",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @DeleteMapping("/{id}")
  public ResponseEntity<LessonDTO> handle(@PathVariable String id) {
    MongoDB.validateObjectId(id);
    var existingCourse = this.courseRepository.findByLessonIdOrThrow(id);
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    this.validateUserCourseAccessService.execute(userId, existingCourse.getId());

    var deletedLesson = this.useCase.execute(id);

    return deletedLesson != null
        ? ResponseEntity.ok(LessonDTO.fromEntity(deletedLesson))
        : ResponseEntity.noContent().build();
  }
}
