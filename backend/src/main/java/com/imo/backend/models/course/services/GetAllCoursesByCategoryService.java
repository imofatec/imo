package com.imo.backend.models.course.services;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCoursesByCategoryService {

    private final CourseRepository courseRepository;

    public GetAllCoursesByCategoryService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> execute(String category) {
        return courseRepository.findAllByCategory(category);
    }
}

