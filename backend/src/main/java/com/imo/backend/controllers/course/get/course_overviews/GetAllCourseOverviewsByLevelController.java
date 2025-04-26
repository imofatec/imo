package com.imo.backend.controllers.course.get.course_overviews;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.services.get.course_overviews.GetAllCourseOverviewsByLevelService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCourseOverviewsByLevelController extends CourseController {

    private final GetAllCourseOverviewsByLevelService getAllCourseOverviewsByLevelService;

    public GetAllCourseOverviewsByLevelController(GetAllCourseOverviewsByLevelService getAllCourseOverviewsByLevelService) {
        this.getAllCourseOverviewsByLevelService = getAllCourseOverviewsByLevelService;
    }

    @Operation(summary = "Get all course overviews by level")
    @GetMapping("/overviews/level/{slugLevel}")
    public ResponseEntity<List<CourseOverview>> handle(@PathVariable String slugLevel) {
        var courseOverviews = getAllCourseOverviewsByLevelService.execute(slugLevel);
        return ResponseEntity.ok(courseOverviews);
    }
}
