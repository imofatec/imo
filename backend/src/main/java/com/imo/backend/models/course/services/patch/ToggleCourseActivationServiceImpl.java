package com.imo.backend.models.course.services.patch;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.services.patch.interfaces.ToggleCourseActivationService;
import org.springframework.stereotype.Service;

@Service
public class ToggleCourseActivationServiceImpl implements ToggleCourseActivationService {

    private final CourseRepository courseRepository;

    public ToggleCourseActivationServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public void execute(String courseId, boolean isActive) {

        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    return new NotFoundException("Curso não encontrado");
                });

        courseRepository.updateCourseStatus(existingCourse.getId(), isActive);
    }

    @Override
    public void activate(String token, String courseId) {
        execute(courseId, true);
    }

    @Override
    public void deactivate(String token, String courseId) {
        execute(courseId, false);
    }

    @Override
    public CourseOverview toggle( String courseId) {
        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    return new NotFoundException("Curso não encontrado");
                });

        boolean newStatus = !existingCourse.isActive();
       var updatedCourse = this.courseRepository.updateCourseStatus(courseId, newStatus);

       return updatedCourse != null ? CourseFactory.createCourseOverview(updatedCourse) : null;
    }
}
