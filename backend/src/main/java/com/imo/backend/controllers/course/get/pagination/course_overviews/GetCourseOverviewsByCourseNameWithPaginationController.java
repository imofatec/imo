package com.imo.backend.controllers.course.get.pagination.course_overviews;

import com.imo.backend.controllers.course.CourseControllerWithPagination;
import com.imo.backend.models.course.dtos.CourseOverview;

import com.imo.backend.models.course.services.get.pagination.course_overviews.GetCourseOverviewsByCourseNameWithPaginationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCourseOverviewsByCourseNameWithPaginationController extends CourseControllerWithPagination {
    private final GetCourseOverviewsByCourseNameWithPaginationService getAllCourseOverviewsByCourseNameWithPaginationService;

    public GetCourseOverviewsByCourseNameWithPaginationController(
            GetCourseOverviewsByCourseNameWithPaginationService getAllCourseOverviewsByCourseNameWithPaginationService
    ) {
        this.getAllCourseOverviewsByCourseNameWithPaginationService = getAllCourseOverviewsByCourseNameWithPaginationService;
    }

    @Operation(summary = "Get all course overviews by course name")
    @GetMapping("/get-all/overviews/cn/{courseName}")
    public ResponseEntity<List<CourseOverview>> handle(
            @PathVariable String courseName,
            @Parameter(description = "Page number to retrieve", example = "0")
            @RequestParam Integer page,
            @Parameter(description = "Size of each page", example = "10")
            @RequestParam Integer size
    ) {
        var courses = getAllCourseOverviewsByCourseNameWithPaginationService.execute(courseName, page, size);
        return ResponseEntity.ok(courses);
    }
}
