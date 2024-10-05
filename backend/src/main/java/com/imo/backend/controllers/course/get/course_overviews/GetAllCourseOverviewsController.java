package com.imo.backend.controllers.course.get.course_overviews;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.services.get.course_overviews.GetAllCourseOverviewsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCourseOverviewsController extends CourseController {

    private final GetAllCourseOverviewsService getAllCourseOverviewsService;

    public GetAllCourseOverviewsController(GetAllCourseOverviewsService getAllCourseOverviewsService) {
        this.getAllCourseOverviewsService = getAllCourseOverviewsService;
    }

    @Operation(summary = "Get all course overviews")
    @GetMapping("/get-all/overviews")
    public ResponseEntity<List<CourseOverview>> handle() {
        var courseOverviews = getAllCourseOverviewsService.execute();
        return ResponseEntity.ok(courseOverviews);
    }
}
