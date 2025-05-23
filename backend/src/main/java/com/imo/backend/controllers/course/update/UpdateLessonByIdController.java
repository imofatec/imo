package com.imo.backend.controllers.course.update;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.dtos.FieldsToUpdateLesson;
import com.imo.backend.models.course.services.authorization.interfaces.ValidateUserCourseAccessService;
import com.imo.backend.models.course.services.get.courses.interfaces.GetCourseByLessonIdService;
import com.imo.backend.models.lessons.dtos.NoCommentsLesson;
import com.imo.backend.models.lessons.services.interfaces.UpdateLessonByIdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateLessonByIdController extends CourseController {
  private final GetCourseByLessonIdService getCourseByLessonIdService;

  private final TokenService tokenService;

  private final ValidateUserCourseAccessService validateUserCourseAccessService;

  private final UpdateLessonByIdService updateLessonByIdService;

  public UpdateLessonByIdController(
      GetCourseByLessonIdService getCourseByLessonIdService,
      TokenService tokenService,
      ValidateUserCourseAccessService validateUserCourseAccessService,
      UpdateLessonByIdService updateLessonByIdService
  ) {
    this.getCourseByLessonIdService = getCourseByLessonIdService;
    this.tokenService = tokenService;
    this.validateUserCourseAccessService = validateUserCourseAccessService;
    this.updateLessonByIdService = updateLessonByIdService;
  }

  @Operation(summary = "Update lesson fields by id")
  @SecurityRequirement(name = "Authorization")
  @PutMapping("/lessons/{lessonId}")
  public ResponseEntity<NoCommentsLesson> handle(
      @PathVariable String lessonId,
      @Valid @RequestBody FieldsToUpdateLesson dto,
      HttpServletRequest request
  ) {
    var existingCourse = this.getCourseByLessonIdService.execute(lessonId);
    var userId = tokenService.getSub(request.getHeader("Authorization")).get("id");

    this.validateUserCourseAccessService.execute(userId, existingCourse.getId());

    var updatedLesson = this.updateLessonByIdService.execute(lessonId, dto);

    return updatedLesson != null
        ? ResponseEntity.ok(updatedLesson)
        : ResponseEntity.noContent().build();
  }
}
