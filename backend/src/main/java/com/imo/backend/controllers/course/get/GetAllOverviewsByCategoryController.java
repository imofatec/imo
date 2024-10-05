package com.imo.backend.controllers.course.get;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.services.get.GetAllCourseOverviewsByCategoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllOverviewsByCategoryController extends CourseController {

    private final GetAllCourseOverviewsByCategoryService getAllCourseOverviewsByCategoryService;

    public GetAllOverviewsByCategoryController(GetAllCourseOverviewsByCategoryService getAllCourseOverviewsByCategoryService) {
        this.getAllCourseOverviewsByCategoryService = getAllCourseOverviewsByCategoryService;
    }

    @GetMapping("/get-all/overviews/{slugCategory}")
    public ResponseEntity<List<CourseOverview>> handle(@PathVariable String slugCategory) {
        var courseOverviews = getAllCourseOverviewsByCategoryService.execute(slugCategory);
        return ResponseEntity.ok(courseOverviews);
    }
}
