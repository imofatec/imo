package com.imo.backend.models.course.services.get.pagination.course_overviews;

import com.imo.backend.lib.Pageable;
import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.strategy.get.many.pagination.GetManyByToListWithPaginationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCourseOverviewsByCourseNameWithPaginationService implements GetManyByToListWithPaginationService<CourseOverview> {
    private final CourseRepository courseRepository;

    public GetCourseOverviewsByCourseNameWithPaginationService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseOverview> execute(String courseName, Integer page, Integer size) {
        var pageable = Pageable.fromPageSize(page, size);

        return courseRepository.findAllByName(courseName, pageable).stream()
                .map(CourseFactory::createCourseOverview)
                .toList();
    }
}
