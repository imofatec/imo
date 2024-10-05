package com.imo.backend.models.course.services.get.pagination.courses;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.strategy.read.pagination.GetManyToListWithPaginationService;
import com.imo.backend.utils.ValidatePageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
        ValidatePageable.validate(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> coursePage = courseRepository.findAll(pageable);
        return coursePage.getContent();
    }
}
