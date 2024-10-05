package com.imo.backend.controllers.course.get.pagination.course_overviews;

import com.imo.backend.controllers.course.CourseControllerPagination;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.services.get.pagination.course_overviews.GetAllCourseOverviewsByCategoryWithPaginationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCourseOverviewsByCategoryWithPaginationController extends CourseControllerPagination {
    private final GetAllCourseOverviewsByCategoryWithPaginationService getAllCourseOverviewsByCategoryWithPaginationService;

    public GetAllCourseOverviewsByCategoryWithPaginationController(
            GetAllCourseOverviewsByCategoryWithPaginationService getAllCourseOverviewsByCategoryWithPaginationService
    ) {
        this.getAllCourseOverviewsByCategoryWithPaginationService = getAllCourseOverviewsByCategoryWithPaginationService;
    }

    @Operation(summary = "Get all course overviews by category")
    @GetMapping("/get-all/overviews/{slugCategory}")
    public ResponseEntity<List<CourseOverview>> handle(
            @PathVariable String slugCategory,
            @Parameter(description = "Page number to retrieve", example = "0")
            @RequestParam Integer page,
            @Parameter(description = "Size of each page", example = "10")
            @RequestParam Integer size) {
        var courseOverviews = getAllCourseOverviewsByCategoryWithPaginationService.execute(slugCategory, page, size);
        return ResponseEntity.ok(courseOverviews);
    }
}
