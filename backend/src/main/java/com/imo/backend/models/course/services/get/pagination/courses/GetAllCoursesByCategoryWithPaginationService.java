package com.imo.backend.models.course.services.get.pagination.courses;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.strategy.get.many.pagination.GetManyByToListWithPaginationService;
import com.imo.backend.utils.ValidatePageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCoursesByCategoryWithPaginationService implements GetManyByToListWithPaginationService<Course> {
    private final CourseRepository courseRepository;

    public GetAllCoursesByCategoryWithPaginationService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> execute(String slugCategory, Integer page, Integer size) {
        ValidatePageable.validate(page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Course> courses = courseRepository.findAllBySlugCategory(slugCategory, pageable);
        return courses.getContent();
    }
}
