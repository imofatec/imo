package com.imo.backend.controllers.user.update;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.user.dtos.UserCourseProgress;
import com.imo.backend.models.user.services.update.UpdateUserCourseProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateUserCourseProgressController extends UserController {
    private final UpdateUserCourseProgressService updateUserCourseProgressService;

    public UpdateUserCourseProgressController(UpdateUserCourseProgressService updateUserCourseProgressService) {
        this.updateUserCourseProgressService = updateUserCourseProgressService;
    }

    @Operation(summary = "Update the progress of any course you started")
    @SecurityRequirement(name = "Authorization")
    @PutMapping("/update-progress/{courseId}")
    public ResponseEntity<UserCourseProgress> handle(@PathVariable String courseId, HttpServletRequest request) {
        var updatedProgress = updateUserCourseProgressService.execute(courseId, null, request.getHeader("Authorization"));
        return ResponseEntity.ok(updatedProgress);
    }
}
