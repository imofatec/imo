package com.imo.backend.models.course.services.get.categories;

import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.Category;
import com.imo.backend.models.strategy.get.many.to_set.GetManyToSetService;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GetAllCategoriesService implements GetManyToSetService<Category> {
    private final CourseRepository courseRepository;

    public GetAllCategoriesService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Set<Category> execute() {
        return courseRepository.findAll().stream()
                .map(course -> new Category(course.getCategory(), course.getSlugCategory()))
                .collect(Collectors.toSet());
    }
}
