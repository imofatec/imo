package com.imo.backend.controllers.course.patch;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.services.authorization.interfaces.ValidateUserCourseAccessService;
import com.imo.backend.models.course.services.patch.interfaces.ToggleCourseActivationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateCourseStatusController extends CourseController {

    private final ToggleCourseActivationService toggleCourseActivationService;

    private final TokenService tokenService;

    private final ValidateUserCourseAccessService validateUserCourseAccessService;

    public UpdateCourseStatusController(
        ToggleCourseActivationService toggleCourseActivationService,
        TokenService tokenService,
        ValidateUserCourseAccessService validateUserCourseAccessService
    ) {
        this.toggleCourseActivationService = toggleCourseActivationService;
      this.tokenService = tokenService;
      this.validateUserCourseAccessService = validateUserCourseAccessService;
    }


    @Operation(summary = "Toggle Course Status")
    @SecurityRequirement(name = "Authorization")
    @PatchMapping("/{courseId}")
    public ResponseEntity<CourseOverview> handle(HttpServletRequest request, @PathVariable String courseId) {
        var userId = this.tokenService.getSub(request.getHeader("Authorization")).get("id");

        this.validateUserCourseAccessService.execute(userId, courseId);

        var updatedCourse = toggleCourseActivationService.toggle(courseId);

        return updatedCourse != null
            ? ResponseEntity.ok(updatedCourse)
            : ResponseEntity.noContent().build();
    }
}