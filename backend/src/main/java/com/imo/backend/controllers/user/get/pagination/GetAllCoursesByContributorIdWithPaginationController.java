package com.imo.backend.controllers.user.get.pagination;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.services.get.pagination.courses.GetAllCoursesByContributorIdWithPaginationService;
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
public class GetAllCoursesByContributorIdWithPaginationController extends UserController {

    private final GetAllCoursesByContributorIdWithPaginationService getAllCoursesByContributorIdWithPaginationService;

    public GetAllCoursesByContributorIdWithPaginationController(
            GetAllCoursesByContributorIdWithPaginationService getAllCoursesByContributorIdWithPaginationService
    ) {
        this.getAllCoursesByContributorIdWithPaginationService = getAllCoursesByContributorIdWithPaginationService;
    }

    @Operation(summary = "Get contributions by authenticated user")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/contributions")
    public ResponseEntity<List<Course>> handle(
            HttpServletRequest request,
            @Parameter(description = "Page number to retrieve", example = "0")
            @RequestParam Integer page,
            @Parameter(description = "Size of each page", example = "10")
            @RequestParam Integer size
    ) {
        var courseOverviews = getAllCoursesByContributorIdWithPaginationService
                .execute(request.getHeader("Authorization"), page, size);
        return ResponseEntity.ok(courseOverviews);
    }
}

