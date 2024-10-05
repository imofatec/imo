package com.imo.backend.controllers.course.get;

import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.services.get.GetAllCourseOverviewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetAllOverviewsController extends CourseController {

    private final GetAllCourseOverviewsService getAllCourseOverviewsService;

    public GetAllOverviewsController(GetAllCourseOverviewsService getAllCourseOverviewsService) {
        this.getAllCourseOverviewsService = getAllCourseOverviewsService;
    }

    @GetMapping("/get-all/overviews")
    public ResponseEntity<List<CourseOverview>> handle() {
        var courseOverviews = getAllCourseOverviewsService.execute();
        return ResponseEntity.ok(courseOverviews);
    }
}
