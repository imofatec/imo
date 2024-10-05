package com.imo.backend.models.course.services.get.pagination.course_overviews;

import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.strategy.get.many.pagination.GetManyByToListWithPaginationService;
import com.imo.backend.utils.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCourseOverviewsByCategoryWithPaginationService
        implements GetManyByToListWithPaginationService<CourseOverview> {
    private final CourseRepository courseRepository;

    public GetAllCourseOverviewsByCategoryWithPaginationService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseOverview> execute(String slugCategory, Integer page, Integer size) {
        var pageable = Pageable.fromPageSize(page, size);

        return courseRepository.findAllBySlugCategory(slugCategory, pageable).stream()
                .map(CourseFactory::createCourseOverview)
                .toList();
    }
}
