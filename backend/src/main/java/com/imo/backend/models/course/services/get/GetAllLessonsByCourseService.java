package com.imo.backend.models.course.services.get;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.lessons.Lesson;
import com.imo.backend.models.strategy.read.GetManyByToListService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllLessonsByCourseService implements GetManyByToListService<Lesson> {
    private final CourseRepository courseRepository;

    public GetAllLessonsByCourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Lesson> execute(String slugCourse) {
        var courses = courseRepository.findBySlugCourse(slugCourse);

        if (courses.isEmpty()) {
            throw new NotFoundException(String.format("O curso %s não foi encontrado", slugCourse));
        }

        return courses.get().getLessons();
    }
}
