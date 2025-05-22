package com.imo.backend.controllers.course.update;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.FieldsToUpdateCourse;
import com.imo.backend.models.course.services.update.UpdateCourseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UpdateCourseController extends CourseController {

  private final UpdateCourseServiceImpl updateCourseService;

  public UpdateCourseController(UpdateCourseServiceImpl updateCourseService) {
    this.updateCourseService = updateCourseService;
  }

  @Operation(summary = "Update course content")
  @SecurityRequirement(name = "Authorization")
  @PutMapping("/{courseId}")
  public ResponseEntity<Course> handle(HttpServletRequest request,
                                       @PathVariable String courseId,
                                       @RequestBody FieldsToUpdateCourse dto) {
    
                                        var userId = request.getHeader("Authorization");
    Course updated = updateCourseService.execute(userId, courseId, dto);
    return ResponseEntity.ok(updated);
  }
}
