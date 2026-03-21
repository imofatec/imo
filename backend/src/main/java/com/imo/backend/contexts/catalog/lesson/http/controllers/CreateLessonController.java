package com.imo.backend.contexts.catalog.lesson.http.controllers;

import com.imo.backend.contexts.catalog.course.http.middlewares.ValidateUserCourseAccessService;
import com.imo.backend.contexts.catalog.lesson.http.dtos.CreateLessonRequest;
import com.imo.backend.contexts.catalog.lesson.http.dtos.LessonResponseDTO;
import com.imo.backend.contexts.catalog.lesson.usecases.CreateLessonUseCase;
import com.imo.backend.contexts.common.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
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

  private final CreateLessonUseCase createLessonService;

  public CreateLessonController(
      ValidateUserCourseAccessService validateUserCourseAccessService,
      CreateLessonUseCase createLessonService
  ) {
    this.validateUserCourseAccessService = validateUserCourseAccessService;
    this.createLessonService = createLessonService;
  }

  @Operation(summary = "Add new lesson in a course")
  @SecurityRequirement(name = "Authorization")
  @PostMapping("/{courseId}")
  public ResponseEntity<LessonResponseDTO> handle(
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

    var newLesson = this.createLessonService.execute(dto.toCommand(), courseId);

    return new ResponseEntity<>(LessonResponseDTO.fromEntity(newLesson), HttpStatus.CREATED);
  }
}
