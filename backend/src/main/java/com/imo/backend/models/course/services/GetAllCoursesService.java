package com.imo.backend.models.course.services;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCoursesService {

    private final CourseRepository courseRepository;

    public GetAllCoursesService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> execute() {
        return courseRepository.findAll();
    }
}

