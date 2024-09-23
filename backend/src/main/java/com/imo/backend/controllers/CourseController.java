package com.imo.backend.controllers;

import com.imo.backend.models.course.dtos.Category;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.CreateCourseRequest;
import com.imo.backend.models.course.dtos.CreateCourseResponse;
import com.imo.backend.models.course.services.create.CreateCourseService;
import com.imo.backend.models.course.services.get.*;
import com.imo.backend.models.lessons.Lesson;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CreateCourseService createCourseService;

    private final GetAllCoursesService getAllCoursesService;

    private final GetAllCourseOverviews getAllCourseOverviews;

    private final GetAllCategoriesService getAllCategoriesService;

    private final GetAllCoursesByCategoryService getAllCoursesByCategoryService;

    private final GetAllCourseOverviewsByCategoryService getAllCourseOverviewsByCategoryService;

    private final GetAllLessonsByCourseService getAllLessonsByCourseService;

    public CourseController(
            CreateCourseService createCourseService,
            GetAllCoursesByCategoryService getAllCoursesByCategoryService,
            GetAllCoursesService getAllCoursesService,
            GetAllCourseOverviews getAllCourseOverviews,
            GetAllCategoriesService getAllCategoriesService,
            GetAllCourseOverviewsByCategoryService getAllCourseOverviewsByCategoryService,
            GetAllLessonsByCourseService getAllLessonsByCourseService) {
        this.createCourseService = createCourseService;
        this.getAllCoursesByCategoryService = getAllCoursesByCategoryService;
        this.getAllCoursesService = getAllCoursesService;
        this.getAllCourseOverviews = getAllCourseOverviews;
        this.getAllCategoriesService = getAllCategoriesService;
        this.getAllCourseOverviewsByCategoryService = getAllCourseOverviewsByCategoryService;
        this.getAllLessonsByCourseService = getAllLessonsByCourseService;
    }

    @SecurityRequirement(name = "Authorization")
    @PostMapping("/create")
    public ResponseEntity<CreateCourseResponse> create(@Valid @RequestBody
                                                       CreateCourseRequest createCourseRequest,
                                                       HttpServletRequest request) {
        var newCourse = createCourseService.execute(createCourseRequest, request.getHeader("Authorization"));
        return new ResponseEntity<>(newCourse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<Course>> getAllCourses() {
        var courses = getAllCoursesService.execute();
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/get-all/{slugCategory}")
    public ResponseEntity<List<Course>> getAllCoursesByCategory(@PathVariable String slugCategory) {
        var courses = getAllCoursesByCategoryService.execute(slugCategory);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/get-all/overviews")
    public ResponseEntity<List<CourseOverview>> getAllOverviews() {
        var courseOverviews = getAllCourseOverviews.execute();
        return ResponseEntity.ok(courseOverviews);
    }

    @GetMapping("/get-all/overviews/{slugCategory}")
    public ResponseEntity<List<CourseOverview>> getAllOverviewsByCategory(@PathVariable String slugCategory) {
        var courseOverviews = getAllCourseOverviewsByCategoryService.execute(slugCategory);
        return ResponseEntity.ok(courseOverviews);
    }

    @GetMapping("/get-all/categories")
    public ResponseEntity<Set<Category>> getAllCategories() {
        var categories = getAllCategoriesService.execute();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/get-all/lessons/{slugCourse}")
    public ResponseEntity<List<Lesson>> getAllLessonsByCourse(@PathVariable String slugCourse) {
        var lessons = getAllLessonsByCourseService.execute(slugCourse);
        return ResponseEntity.ok(lessons);
    }
}
