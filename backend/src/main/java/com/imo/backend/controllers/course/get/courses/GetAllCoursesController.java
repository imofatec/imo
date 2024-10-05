package com.imo.backend.controllers.course.get.courses;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.services.get.courses.GetAllCoursesService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCoursesController extends CourseController {

    private final GetAllCoursesService getAllCoursesService;

    public GetAllCoursesController(GetAllCoursesService getAllCoursesService) {
        this.getAllCoursesService = getAllCoursesService;
    }

    @Operation(summary = "Get all courses")
    @GetMapping("/get-all")
    public ResponseEntity<List<Course>> handle() {
        var courses = getAllCoursesService.execute();
        return ResponseEntity.ok(courses);
    }
}
