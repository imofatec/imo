package com.imo.backend.controllers.course.update;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.dtos.FieldsToUpdateCourse;
import com.imo.backend.models.course.services.authorization.interfaces.ValidateUserCourseAccessService;
import com.imo.backend.models.course.services.update.interfaces.UpdateCourseByIdService;
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
public class UpdateCourseByIdController extends CourseController {

  private final UpdateCourseByIdService updateCourseByIdService;

  private final TokenService tokenService;

  private final ValidateUserCourseAccessService validateUserCourseAccessService;

  public UpdateCourseByIdController(
      UpdateCourseByIdService updateCourseByIdService,
      TokenService tokenService,
      ValidateUserCourseAccessService validateUserCourseAccessService
  ) {
    this.updateCourseByIdService = updateCourseByIdService;
    this.tokenService = tokenService;
    this.validateUserCourseAccessService = validateUserCourseAccessService;
  }

  @Operation(summary = "Update course fields by id")
  @SecurityRequirement(name = "Authorization")
  @PutMapping("/{courseId}")
  public ResponseEntity<CourseOverview> handle(
      HttpServletRequest request,
      @PathVariable String courseId,
      @Valid @RequestBody FieldsToUpdateCourse dto
  ) {
    var bearerToken = request.getHeader("Authorization");
    var userId = this.tokenService.getSub(bearerToken).get("id");

    this.validateUserCourseAccessService.execute(userId, courseId);

    var updatedCourse = this.updateCourseByIdService.execute(courseId, dto);

    return updatedCourse == null
        ? ResponseEntity.noContent().build()
        : ResponseEntity.ok(updatedCourse);
  }
}
