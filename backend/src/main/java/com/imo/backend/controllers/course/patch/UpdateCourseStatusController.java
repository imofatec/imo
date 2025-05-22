package com.imo.backend.controllers.course.patch;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.services.patch.interfaces.ToggleCourseActivationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class UpdateCourseStatusController extends CourseController {

    private final ToggleCourseActivationService toggleCourseActivationService;

    public UpdateCourseStatusController(ToggleCourseActivationService toggleCourseActivationService) {
        this.toggleCourseActivationService = toggleCourseActivationService;
    }


    @Operation(summary = "Toggle Course Status")
    @SecurityRequirement(name = "Authorization")
    @PatchMapping("/{courseId}")
    public ResponseEntity<Void> handle(HttpServletRequest request, @PathVariable String courseId) {
        String token = request.getHeader("Authorization");
        toggleCourseActivationService.toggle(token, courseId);
        return ResponseEntity.noContent().build();
    }
}