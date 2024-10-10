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

import java.util.List;

@RestController
public class CreateManyCoursesController extends CourseController {
    private final CreateCourseService createCourseService;

    public CreateManyCoursesController(CreateCourseService createCourseService) {
        this.createCourseService = createCourseService;
    }

    @Operation(summary = "Create many courses")
    @SecurityRequirement(name = "Authorization")
    @PostMapping("/create-many")
    public ResponseEntity<List<CreateCourseResponse>> handle(@Valid @RequestBody
                                                             List<CreateCourseRequest> createCoursesRequest,
                                                             HttpServletRequest request) {
        var newCourses = createCoursesRequest.stream()
                .map(createCourseRequest ->
                        createCourseService.execute(
                                createCourseRequest,
                                request.getHeader("Authorization")))
                .toList();

        return new ResponseEntity<>(newCourses, HttpStatus.ACCEPTED);
    }
}
