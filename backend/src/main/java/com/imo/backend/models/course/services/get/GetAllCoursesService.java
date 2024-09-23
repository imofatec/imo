package com.imo.backend.models.course.services.get;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.strategy.read.GetManyToListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCoursesService implements GetManyToListService<Course> {

    private final CourseRepository courseRepository;

    public GetAllCoursesService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> execute() {
        return courseRepository.findAll();
    }
}
