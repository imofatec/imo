package com.imo.backend.controllers.course.get;

import com.imo.backend.controllers.CourseController;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.services.get.GetAllCoursesByCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetCourseByCategoryController extends CourseController {

    private final GetAllCoursesByCategoryService getAllCoursesByCategoryService;

    public GetCourseByCategoryController(GetAllCoursesByCategoryService getAllCoursesByCategoryService) {
        this.getAllCoursesByCategoryService = getAllCoursesByCategoryService;
    }

    @GetMapping("/get-all/{slugCategory}")
    public ResponseEntity<List<Course>> handle(@PathVariable String slugCategory) {
        var courses = getAllCoursesByCategoryService.execute(slugCategory);
        return ResponseEntity.ok(courses);
    }
}
