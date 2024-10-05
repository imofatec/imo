package com.imo.backend.controllers.course.get.courses;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.services.get.courses.GetAllCoursesByCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCoursesByCategoryController extends CourseController {

    private final GetAllCoursesByCategoryService getAllCoursesByCategoryService;

    public GetAllCoursesByCategoryController(GetAllCoursesByCategoryService getAllCoursesByCategoryService) {
        this.getAllCoursesByCategoryService = getAllCoursesByCategoryService;
    }

    @Operation(summary = "Get all courses by category")
    @GetMapping("/get-all/{slugCategory}")
    public ResponseEntity<List<Course>> handle(@PathVariable String slugCategory) {
        var courses = getAllCoursesByCategoryService.execute(slugCategory);
        return ResponseEntity.ok(courses);
    }
}
