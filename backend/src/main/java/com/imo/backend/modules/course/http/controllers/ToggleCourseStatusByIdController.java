package com.imo.backend.modules.course.http.controllers;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.actions.ToggleCourseStatusByIdAction;
import com.imo.backend.modules.course.http.middlewares.ValidateUserCourseAccessService;
import com.imo.backend.utils.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ToggleCourseStatusByIdController extends CourseController {

  private final ToggleCourseStatusByIdAction toggleCourseStatusByIdAction;

  private final ValidateUserCourseAccessService validateUserCourseAccessService;

  public ToggleCourseStatusByIdController(
      ToggleCourseStatusByIdAction toggleCourseStatusByIdAction,
      ValidateUserCourseAccessService validateUserCourseAccessService
  ) {
    this.toggleCourseStatusByIdAction = toggleCourseStatusByIdAction;
    this.validateUserCourseAccessService = validateUserCourseAccessService;
  }

  @Operation(summary = "Toggle course status")
  @SecurityRequirement(name = "Authorization")
  @PatchMapping("/{id}")
  public ResponseEntity<Course> handle(
      @PathVariable
      String id
  ) {
    MongoDB.validateObjectId(id);
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    this.validateUserCourseAccessService.execute(userId, id);

    var updatedCourse = toggleCourseStatusByIdAction.execute(id);

    return updatedCourse != null
        ? ResponseEntity.ok(updatedCourse)
        : ResponseEntity.noContent().build();
  }
}