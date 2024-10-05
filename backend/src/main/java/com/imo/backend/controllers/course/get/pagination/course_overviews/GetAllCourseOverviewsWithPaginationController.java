package com.imo.backend.controllers.course.get.pagination.course_overviews;

import com.imo.backend.controllers.course.CourseControllerWithPagination;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.services.get.pagination.course_overviews.GetAllCourseOverviewsWithPaginationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCourseOverviewsWithPaginationController extends CourseControllerWithPagination {
    private final GetAllCourseOverviewsWithPaginationService getAllCourseOverviewsWithPaginationService;

    public GetAllCourseOverviewsWithPaginationController(
            GetAllCourseOverviewsWithPaginationService getAllCourseOverviewsWithPaginationService
    ) {
        this.getAllCourseOverviewsWithPaginationService = getAllCourseOverviewsWithPaginationService;
    }

    @Operation(summary = "Get all course overviews")
    @GetMapping("/get-all/overviews")
    public ResponseEntity<List<CourseOverview>> handle(
            @Parameter(description = "Page number to retrieve", example = "0")
            @RequestParam Integer page,
            @Parameter(description = "Size of each page", example = "10")
            @RequestParam Integer size) {
        var courseOverviews = getAllCourseOverviewsWithPaginationService.execute(page, size);
        return ResponseEntity.ok(courseOverviews);
    }

}
