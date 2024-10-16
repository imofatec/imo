package com.imo.backend.controllers.course.get.lessons;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.services.get.lessons.GetAllLessonsByCourseService;
import com.imo.backend.models.lessons.Lesson;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllLessonsByCourseController extends CourseController {

    private final GetAllLessonsByCourseService getAllLessonsByCourseService;

    public GetAllLessonsByCourseController(GetAllLessonsByCourseService getAllLessonsByCourseService) {
        this.getAllLessonsByCourseService = getAllLessonsByCourseService;
    }

    @Operation(summary = "Get all lessons by course")
    @GetMapping("/get-all/lessons/{slugCourse}")
    public ResponseEntity<List<Lesson>> handle(@PathVariable String slugCourse) {
        var lessons = getAllLessonsByCourseService.execute(slugCourse);
        return ResponseEntity.ok(lessons);
    }
}
