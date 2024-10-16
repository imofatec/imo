package com.imo.backend.models.course.services.get.courses;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.strategy.get.many.to_list.GetManyByToListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCoursesByCategoryService implements GetManyByToListService<Course> {

    private final CourseRepository courseRepository;

    public GetAllCoursesByCategoryService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> execute(String slugCategory) {
        return courseRepository.findAllBySlugCategory(slugCategory);
    }
}

