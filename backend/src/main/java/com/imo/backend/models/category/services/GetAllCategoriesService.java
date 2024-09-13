package com.imo.backend.models.category.services;

import com.imo.backend.models.category.Category;
import com.imo.backend.models.category.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCategoriesService {

    private final CategoryRepository categoryRepository;

    public GetAllCategoriesService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> execute() {
        return categoryRepository.findAll();
    }
}
