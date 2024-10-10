package com.imo.backend.controllers.user.get;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.course.dtos.CourseProgress;
import com.imo.backend.models.user.services.get.GetCourseProgressByIdService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCourseProgressByIdController extends UserController {
    private final GetCourseProgressByIdService getCourseProgressByIdService;

    public GetCourseProgressByIdController(GetCourseProgressByIdService getCourseProgressByIdService) {
        this.getCourseProgressByIdService = getCourseProgressByIdService;
    }

    @Operation(summary = "Get course progress by course id")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/course-progress/{courseId}")
    public ResponseEntity<CourseProgress> handle(@PathVariable String courseId, HttpServletRequest request) {
        var courseProgress = getCourseProgressByIdService.execute(courseId, request.getHeader("Authorization"));
        return ResponseEntity.ok(courseProgress);
    }
}
