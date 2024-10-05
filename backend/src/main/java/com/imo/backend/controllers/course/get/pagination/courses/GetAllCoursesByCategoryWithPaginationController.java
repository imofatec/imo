package com.imo.backend.controllers.course.get.pagination.courses;

import com.imo.backend.controllers.course.CourseControllerWithPagination;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.services.get.pagination.courses.GetAllCoursesByCategoryWithPaginationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCoursesByCategoryWithPaginationController extends CourseControllerWithPagination {
    private final GetAllCoursesByCategoryWithPaginationService getAllCoursesByCategoryWithPaginationService;

    public GetAllCoursesByCategoryWithPaginationController(
            GetAllCoursesByCategoryWithPaginationService getAllCoursesByCategoryWithPaginationService
    ) {
        this.getAllCoursesByCategoryWithPaginationService = getAllCoursesByCategoryWithPaginationService;
    }

    @Operation(summary = "Get all courses by category")
    @GetMapping("/get-all/{slugCategory}")
    public ResponseEntity<List<Course>> handle(
            @PathVariable String slugCategory,
            @Parameter(description = "Page number to retrieve", example = "0")
            @RequestParam Integer page,
            @Parameter(description = "Size of each page", example = "10")
            @RequestParam Integer size) {
        var courses = getAllCoursesByCategoryWithPaginationService.execute(slugCategory, page, size);
        return ResponseEntity.ok(courses);
    }
}
