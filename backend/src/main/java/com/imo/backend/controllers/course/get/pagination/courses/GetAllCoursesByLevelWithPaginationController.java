package com.imo.backend.controllers.course.get.pagination.courses;

import com.imo.backend.controllers.course.CourseControllerWithPagination;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.services.get.pagination.courses.GetAllCoursesByLevelWithPaginationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCoursesByLevelWithPaginationController extends CourseControllerWithPagination {
    private final GetAllCoursesByLevelWithPaginationService getAllCoursesByLevelWithPaginationService;

    public GetAllCoursesByLevelWithPaginationController(
            GetAllCoursesByLevelWithPaginationService getAllCoursesByLevelWithPaginationService
    ) {
        this.getAllCoursesByLevelWithPaginationService = getAllCoursesByLevelWithPaginationService;
    }

    @Operation(summary = "Get all courses by level")
    @GetMapping("/get-all/nk/{slugLevel}")
    public ResponseEntity<List<Course>> handle(
            @PathVariable String slugLevel,
            @Parameter(description = "Page number to retrieve", example = "0")
            @RequestParam Integer page,
            @Parameter(description = "Size of each page", example = "10")
            @RequestParam Integer size) {
        var courses = getAllCoursesByLevelWithPaginationService.execute(slugLevel, page, size);
        return ResponseEntity.ok(courses);
    }
}
