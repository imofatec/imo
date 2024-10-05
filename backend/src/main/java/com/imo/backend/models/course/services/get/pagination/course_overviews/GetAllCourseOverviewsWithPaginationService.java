package com.imo.backend.models.course.services.get.pagination.course_overviews;

import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.strategy.read.pagination.GetManyToListWithPaginationService;
import com.imo.backend.utils.ValidatePageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCourseOverviewsWithPaginationService implements GetManyToListWithPaginationService<CourseOverview> {
    private final CourseRepository courseRepository;

    public GetAllCourseOverviewsWithPaginationService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseOverview> execute(Integer page, Integer size) {
        ValidatePageable.validate(page, size);
        Pageable pageable = PageRequest.of(page, size);

        return courseRepository.findAll(pageable).stream()
                .map(CourseFactory::createCourseOverview)
                .toList();
    }
}
