package com.imo.backend.models.course.services.get;

import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.strategy.read.GetManyByToListService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllCourseOverviewsByCategoryService implements GetManyByToListService<CourseOverview> {
    private final CourseRepository courseRepository;

    public GetAllCourseOverviewsByCategoryService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseOverview> execute(String slug) {
        return courseRepository.findAllBySlugCategory(slug).stream()
                .map(CourseFactory::createCourseOverview)
                .collect(Collectors.toList());
    }
}
