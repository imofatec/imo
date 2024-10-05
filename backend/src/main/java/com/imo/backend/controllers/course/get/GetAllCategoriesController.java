package com.imo.backend.controllers.course.get;

import com.imo.backend.controllers.CourseController;
import com.imo.backend.models.course.dtos.Category;
import com.imo.backend.models.course.services.get.GetAllCategoriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class GetAllCategoriesController extends CourseController {

    private final GetAllCategoriesService getAllCategoriesService;

    public GetAllCategoriesController(GetAllCategoriesService getAllCategoriesService) {
        this.getAllCategoriesService = getAllCategoriesService;
    }

    @GetMapping("/get-all/categories")
    public ResponseEntity<Set<Category>> handle() {
        var categories = getAllCategoriesService.execute();
        return ResponseEntity.ok(categories);
    }
}
