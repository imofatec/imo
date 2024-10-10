package com.imo.backend.controllers.user.get;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.course.dtos.CourseProgress;
import com.imo.backend.models.user.services.get.GetCoursesProgressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCoursesProgressController extends UserController {
    private final GetCoursesProgressService getCoursesProgressService;

    public GetCoursesProgressController(GetCoursesProgressService getCoursesProgressService) {
        this.getCoursesProgressService = getCoursesProgressService;
    }

    @Operation(summary = "Get your courses progress")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/courses-progress")
    public ResponseEntity<List<CourseProgress>> handle(HttpServletRequest request) {
        var coursesProgress = getCoursesProgressService.execute(request.getHeader("Authorization"));
        return ResponseEntity.ok(coursesProgress);
    }
}
