package com.imo.backend.controllers.course.get.courses;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.services.get.courses.GetAllCoursesByLevelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCoursesByLevelController extends CourseController {

    private final GetAllCoursesByLevelService getAllCoursesByLevelService;

    public GetAllCoursesByLevelController(GetAllCoursesByLevelService getAllCoursesByLevelService) {
        this.getAllCoursesByLevelService = getAllCoursesByLevelService;
    }

    @Operation(summary = "Get all courses by level")
    @GetMapping("/level/{slugLevel}")
    public ResponseEntity<List<Course>> handle(@PathVariable String slugLevel) {
        var courses = getAllCoursesByLevelService.execute(slugLevel);
        return ResponseEntity.ok(courses);
    }
}
