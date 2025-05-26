package com.imo.backend.models.course.services.get.courses;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.services.get.courses.interfaces.GetCourseByIdService;
import org.springframework.stereotype.Service;

@Service
public class GetCourseByIdServiceImpl  implements GetCourseByIdService {

    private final CourseRepository courseRepository;

    public GetCourseByIdServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course execute(String courseId) {
        Course course = courseRepository.findCourseById(courseId);

        if (course != null) {
            return course;
        }
        throw new NotFoundException("Curso nao encontrado");
    }
}
