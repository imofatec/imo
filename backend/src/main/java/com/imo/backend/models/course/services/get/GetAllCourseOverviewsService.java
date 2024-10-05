package com.imo.backend.models.course.services.get;

import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.strategy.read.GetManyToListService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GetAllCourseOverviewsService implements GetManyToListService<CourseOverview> {
    private final CourseRepository courseRepository;

    public GetAllCourseOverviewsService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<CourseOverview> execute() {
        return courseRepository.findAll().stream()
                .map(CourseFactory::createCourseOverview)
                .collect(Collectors.toList());
    }
}
