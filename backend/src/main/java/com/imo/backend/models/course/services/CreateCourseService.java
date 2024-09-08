package com.imo.backend.models.course.services;


import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.CreateCourseDto;
import com.imo.backend.models.lessons.Lesson;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreateCourseService {
    private final CourseRepository courseRepository;

    public CreateCourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public Course execute(CreateCourseDto createCourseDto) {

        Course course = Course.fromCreateDto(createCourseDto);

        if (course.getCreatedAt() == null){
            course.setCreatedAt(LocalDateTime.now());
        }

        List<Lesson> formattedLessons = course.getLessons();

        for (int i = 0; i < formattedLessons.size(); i++) {
            var item = formattedLessons.get(i);
            item.setIndex(i + 1);
            item.formatLink();
            item.setUploadedAt(LocalDateTime.now());
        }

        course.setLessons(formattedLessons);

        return courseRepository.save(course);
    }
}
