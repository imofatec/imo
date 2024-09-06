package com.imo.backend.controllers;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.CreateCourseDto;
import com.imo.backend.models.course.services.CreateCourseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CreateCourseService createCourseService;

    public CourseController(CreateCourseService createCourseService) {
        this.createCourseService = createCourseService;
    }

    @PostMapping("/create")
    public ResponseEntity<Course> create(@Valid @RequestBody CreateCourseDto createCourseDto) {
        var newCourse = createCourseService.execute(createCourseDto);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);
    }
}
