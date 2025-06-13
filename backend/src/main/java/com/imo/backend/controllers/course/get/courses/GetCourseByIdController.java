package com.imo.backend.controllers.course.get.courses;


import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.services.get.courses.interfaces.GetCourseByIdService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GetCourseByIdController extends CourseController {

    private final GetCourseByIdService getCourseByIdService;

    public GetCourseByIdController(GetCourseByIdService getCourseByIdService) {
        this.getCourseByIdService = getCourseByIdService;
    }

    @Operation(summary = "Get course by id")
    @GetMapping("/{courseId}")
    public ResponseEntity<Course> execute(@PathVariable String courseId){
        return ResponseEntity.ok(getCourseByIdService.execute(courseId));
    }
}
