package com.imo.backend.controllers.user.get.pagination;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.course.services.get.pagination.course_overviews.GetAllCourseOverviewsByUserCourseProgressesWithPaginationService;
import com.imo.backend.models.user.dtos.UserCourseOverview;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCourseOverviewsByUserCourseProgressWithPaginationController extends UserController {
    private final GetAllCourseOverviewsByUserCourseProgressesWithPaginationService getAllCourseOverviewsByUserCourseProgressesWithPaginationService;

    public GetAllCourseOverviewsByUserCourseProgressWithPaginationController(
            GetAllCourseOverviewsByUserCourseProgressesWithPaginationService getAllCourseOverviewsByUserCourseProgressesWithPaginationService
    ) {
        this.getAllCourseOverviewsByUserCourseProgressesWithPaginationService = getAllCourseOverviewsByUserCourseProgressesWithPaginationService;
    }

    @Operation(summary = "Get course overviews from the courses you started")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/courses-progress")
    public ResponseEntity<List<UserCourseOverview>> handle(
            HttpServletRequest request,
            @Parameter(description = "Page number to retrieve", example = "0")
            @RequestParam Integer page,
            @Parameter(description = "Size of each page", example = "10")
            @RequestParam Integer size
    ) {
        var courseOverviews = getAllCourseOverviewsByUserCourseProgressesWithPaginationService.execute(request.getHeader("Authorization"), page, size);
        return ResponseEntity.ok(courseOverviews);
    }
}
