package com.imo.backend.controllers;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.CreateCourseRequest;
import com.imo.backend.models.course.dtos.CreateCourseResponse;
import com.imo.backend.models.course.services.CreateCourseService;
import com.imo.backend.models.course.services.GetAllCoursesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CreateCourseService createCourseService;
    private final GetAllCoursesService getAllCoursesService;;

    public CourseController(CreateCourseService createCourseService, GetAllCoursesService getAllCoursesService) {
        this.createCourseService = createCourseService;
        this.getAllCoursesService = getAllCoursesService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateCourseResponse> create(@Valid @RequestBody CreateCourseRequest createCourseRequest) {
        var newCourse = createCourseService.execute(createCourseRequest);
        return new ResponseEntity<>(newCourse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Course>> getAll() {
        var courses = getAllCoursesService.execute();
        return new ResponseEntity<>(courses, HttpStatus.OK);
    }
}
