package com.imo.backend.models.course.services.get.pagination.courses;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.strategy.get.many.pagination.GetManyToListWithPaginationService;
import com.imo.backend.lib.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCoursesWithPaginationService implements GetManyToListWithPaginationService<Course> {
    private final CourseRepository courseRepository;

    public GetAllCoursesWithPaginationService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> execute(Integer page, Integer size) {
        var pageable = Pageable.fromPageSize(page, size);

        Page<Course> coursePage = courseRepository.findAll(pageable);
        return coursePage.getContent();
    }
}
