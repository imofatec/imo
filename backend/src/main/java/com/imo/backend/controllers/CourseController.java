package com.imo.backend.controllers;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.CreateCourseRequest;
import com.imo.backend.models.course.dtos.CreateCourseResponse;
import com.imo.backend.models.course.services.CreateCourseService;
import com.imo.backend.models.course.services.GetAllCoursesByCategoryService;
import com.imo.backend.models.course.services.GetAllCoursesService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
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

    private final GetAllCoursesService getAllCoursesService;

    public CourseController(
            CreateCourseService createCourseService,
            GetAllCoursesByCategoryService getAllCoursesByCategoryService,
            GetAllCoursesService getAllCoursesService) {
        this.createCourseService = createCourseService;
        this.getAllCoursesByCategoryService = getAllCoursesByCategoryService;
        this.getAllCoursesService = getAllCoursesService;
    }

    @SecurityRequirement(name = "Authorization")
    @PostMapping("/create")
    public ResponseEntity<CreateCourseResponse> create(@Valid @RequestBody
                                                       CreateCourseRequest createCourseRequest,
                                                       HttpServletRequest request) {
        var newCourse = createCourseService.execute(createCourseRequest, request.getHeader("Authorization"));
        return new ResponseEntity<>(newCourse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all/{category}")
    public ResponseEntity<List<Course>> getAllCoursesByCategory(@PathVariable String category) {
        var courses = getAllCoursesByCategoryService.execute(category);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Course>> getAllCourses() {
        var courses = getAllCoursesService.execute();
        return ResponseEntity.ok(courses);
    }
}
