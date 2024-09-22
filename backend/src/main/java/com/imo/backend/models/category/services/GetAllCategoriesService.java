package com.imo.backend.models.category.services;

import com.imo.backend.models.category.Category;
import com.imo.backend.models.category.CategoryRepository;
import com.imo.backend.models.strategy.read.GetManyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCategoriesService implements GetManyService<Category> {

    private final CategoryRepository categoryRepository;

    public GetAllCategoriesService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> execute() {
        return categoryRepository.findAll();
    }
}
