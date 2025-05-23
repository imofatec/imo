package com.imo.backend.controllers.course.delete;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.services.delete.interfaces.DeleteLessonFromCourseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class DeleteLessonFromCourseController extends CourseController {

    private final DeleteLessonFromCourseService deleteLessonFromCourseService;

    public DeleteLessonFromCourseController(DeleteLessonFromCourseService deleteLessonFromCourseService) {
        this.deleteLessonFromCourseService = deleteLessonFromCourseService;
    }

    @Operation(summary = "Delete Lesson from Course")
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/{courseId}/lessons/{lessonId}")
    public ResponseEntity<Void> handle(HttpServletRequest request, @PathVariable String courseId, @PathVariable String lessonId) {
        String token = request.getHeader("Authorization");
        deleteLessonFromCourseService.execute(token, courseId, lessonId);
        return ResponseEntity.noContent().build();
    }       

}

