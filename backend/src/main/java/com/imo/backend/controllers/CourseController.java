package com.imo.backend.controllers;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.CreateCourseRequest;
import com.imo.backend.models.course.dtos.CreateCourseResponse;
import com.imo.backend.models.course.services.CreateCourseService;
import com.imo.backend.models.course.services.GetAllCoursesByCategoryService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CreateCourseService createCourseService;

    private final GetAllCoursesByCategoryService getAllCoursesByCategoryService;


    public CourseController(
            CreateCourseService createCourseService,
            GetAllCoursesByCategoryService getAllCoursesByCategoryService
    ) {
        this.createCourseService = createCourseService;
        this.getAllCoursesByCategoryService = getAllCoursesByCategoryService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateCourseResponse> create(@Valid @RequestBody CreateCourseRequest createCourseRequest) {
        var newCourse = createCourseService.execute(createCourseRequest);
        return new ResponseEntity<>(newCourse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all/{category}")
    public ResponseEntity<List<Course>> getAllCoursesByCategory(@PathVariable String category) {
        var courses = getAllCoursesByCategoryService.execute(category);
        return ResponseEntity.ok(courses);
    }
}
