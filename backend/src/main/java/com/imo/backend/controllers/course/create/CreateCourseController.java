package com.imo.backend.controllers.course.create;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.dtos.CreateCourseRequest;
import com.imo.backend.models.course.dtos.CreateCourseResponse;
import com.imo.backend.models.course.services.create.CreateCourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateCourseController extends CourseController{

    private final CreateCourseService createCourseService;

    public CreateCourseController(CreateCourseService createCourseService) {
        this.createCourseService = createCourseService;
    }

    @Operation(summary = "Create a course")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/create")
    public ResponseEntity<CreateCourseResponse> handle(@Valid @RequestBody
                                                       CreateCourseRequest createCourseRequest,
                                                       HttpServletRequest request) {
        var newCourse = createCourseService.execute(createCourseRequest, request.getHeader("Authorization"));
        return new ResponseEntity<>(newCourse, HttpStatus.ACCEPTED);
    }
}
