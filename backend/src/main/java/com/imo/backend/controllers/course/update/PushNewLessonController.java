package com.imo.backend.controllers.course.update;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.services.authorization.interfaces.ValidateUserCourseAccessService;
import com.imo.backend.models.lessons.dtos.CreateLessonDto;
import com.imo.backend.models.lessons.dtos.NoCommentsLesson;
import com.imo.backend.models.lessons.services.interfaces.PushLessonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PushNewLessonController extends CourseController {
  private final PushLessonService pushLessonService;

  private final TokenService tokenService;

  private final ValidateUserCourseAccessService validateUserCourseAccessService;

  public PushNewLessonController(
      PushLessonService pushLessonService, TokenService tokenService,
      ValidateUserCourseAccessService validateUserCourseAccessService
  ) {
    this.pushLessonService = pushLessonService;
    this.tokenService = tokenService;
    this.validateUserCourseAccessService = validateUserCourseAccessService;
  }

  @Operation(summary = "Add new lesson in a course")
  @SecurityRequirement(name = "Authorization")
  @PutMapping("/{courseId}/lessons")
  public ResponseEntity<List<NoCommentsLesson>> handle(
      HttpServletRequest request,
      @PathVariable String courseId,
      @Valid @RequestBody List<CreateLessonDto> dto
  ) {
    var userId = this.tokenService.getSub(request.getHeader("Authorization")).get("id");

    this.validateUserCourseAccessService.execute(userId, courseId);

    var lessons = this.pushLessonService.execute(courseId, dto);

    return lessons != null
        ? new ResponseEntity<>(lessons, HttpStatus.CREATED)
        : ResponseEntity.noContent().build();
  }
}
