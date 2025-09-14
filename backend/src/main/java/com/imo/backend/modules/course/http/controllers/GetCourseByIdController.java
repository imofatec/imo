package com.imo.backend.modules.course.http.controllers;


import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.course.guards.GetCourseByIdGuard;
import com.imo.backend.utils.MongoDB;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetCourseByIdController extends CourseController {

  private final GetCourseByIdGuard getCourseByIdGuard;

  public GetCourseByIdController(GetCourseByIdGuard getCourseByIdGuard) {
    this.getCourseByIdGuard = getCourseByIdGuard;
  }

  @Operation(summary = "Get course by id")
  @GetMapping("/{id}")
  public ResponseEntity<Course> execute(
      @PathVariable
      String id
  ) {
    MongoDB.validateObjectId(id);
    return ResponseEntity.ok(getCourseByIdGuard.execute(id));
  }
}
