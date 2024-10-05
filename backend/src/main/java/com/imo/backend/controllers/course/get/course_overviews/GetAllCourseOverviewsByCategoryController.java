package com.imo.backend.controllers.course.get.course_overviews;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.services.get.course_overviews.GetAllCourseOverviewsByCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllCourseOverviewsByCategoryController extends CourseController {

    private final GetAllCourseOverviewsByCategoryService getAllCourseOverviewsByCategoryService;

    public GetAllCourseOverviewsByCategoryController(GetAllCourseOverviewsByCategoryService getAllCourseOverviewsByCategoryService) {
        this.getAllCourseOverviewsByCategoryService = getAllCourseOverviewsByCategoryService;
    }

    @Operation(summary = "Get all course overviews by category")
    @GetMapping("/get-all/overviews/{slugCategory}")
    public ResponseEntity<List<CourseOverview>> handle(@PathVariable String slugCategory) {
        var courseOverviews = getAllCourseOverviewsByCategoryService.execute(slugCategory);
        return ResponseEntity.ok(courseOverviews);
    }
}
