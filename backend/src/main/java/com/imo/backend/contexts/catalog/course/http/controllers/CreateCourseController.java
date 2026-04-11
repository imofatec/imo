package com.imo.backend.contexts.catalog.course.http.controllers;

import com.imo.backend.contexts.catalog.course.http.dtos.CourseDetailsDTO;
import com.imo.backend.contexts.catalog.course.http.dtos.CreateCourseRequest;
import com.imo.backend.contexts.catalog.course.usecases.CreateCourseUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateCourseController extends CourseController {

  private final CreateCourseUseCase createCourseUseCase;

  public CreateCourseController(CreateCourseUseCase createCourseUseCase) {
    this.createCourseUseCase = createCourseUseCase;
  }

  @Operation(summary = "Create a course")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "201",
            description = "Curso criado com sucesso",
            content = @Content(schema = @Schema(implementation = CourseDetailsDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto
                                    .class))),
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
            responseCode = "409",
            description = "Curso já cadastrado",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @PostMapping()
  public ResponseEntity<CourseDetailsDTO> handle(
      @Valid @RequestBody CreateCourseRequest createCourseRequest) {
    String contributorId = SecurityContextHolder.getContext().getAuthentication().getName();
    var newCourseWithLessons =
        this.createCourseUseCase.execute(
            createCourseRequest.toCommand(contributorId), contributorId);

    return new ResponseEntity<>(newCourseWithLessons, HttpStatus.CREATED);
  }
}
