package com.imo.backend.controllers;

import com.imo.backend.models.category.Category;
import com.imo.backend.models.category.services.GetAllCategoriesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
    
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    private final GetAllCategoriesService getAllCategoriesService;

    public CategoryController(
            GetAllCategoriesService getAllCategoriesService
    ) {
        this.getAllCategoriesService = getAllCategoriesService;
    }

        @GetMapping("/get-all")
        public ResponseEntity<List<Category>> getAllCategories() {
            var categories = getAllCategoriesService.execute();
            return ResponseEntity.ok(categories);
        }

}
