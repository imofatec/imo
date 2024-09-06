package com.imo.backend.models.course.services;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.CreateCourseDto;
import org.springframework.stereotype.Service;

@Service
public class CreateCourseService {
    private final CourseRepository courseRepository;

    public CreateCourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course execute(CreateCourseDto createCourseDto) {
        Course course = Course.fromCreateDto(createCourseDto);
        return courseRepository.save(course);
    }
}
