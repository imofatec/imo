package com.imo.backend.controllers.course.get.pagination.courses;

import com.imo.backend.controllers.course.CourseControllerPagination;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.services.get.pagination.courses.GetAllCoursesWithPaginationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCoursesWithPaginationController extends CourseControllerPagination {
    private final GetAllCoursesWithPaginationService getAllCoursesWithPaginationService;

    public GetAllCoursesWithPaginationController(
            GetAllCoursesWithPaginationService getAllCoursesWithPaginationService
    ) {
        this.getAllCoursesWithPaginationService = getAllCoursesWithPaginationService;
    }

    @Operation(summary = "Get all courses with pagination")
    @GetMapping("/get-all")
    public ResponseEntity<List<Course>> handle(
            @Parameter(description = "Page number to retrieve", example = "0")
            @RequestParam Integer page,
            @Parameter(description = "Size of each page", example = "10")
            @RequestParam Integer size) {
        var courses = getAllCoursesWithPaginationService.execute(page, size);
        return ResponseEntity.ok(courses);
    }
}
