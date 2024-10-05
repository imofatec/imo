package com.imo.backend.controllers.course.get;

import com.imo.backend.controllers.CourseController;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.services.get.GetAllCoursesService;
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

    @GetMapping("/get-all")
    public ResponseEntity<List<Course>> handle() {
        var courses = getAllCoursesService.execute();
        return ResponseEntity.ok(courses);
    }
}
