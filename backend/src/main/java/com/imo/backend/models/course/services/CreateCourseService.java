package com.imo.backend.models.course.services;

import com.imo.backend.exceptions.custom.ConflictException;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.CreateCourseRequest;
import com.imo.backend.models.course.dtos.CreateCourseResponse;
import com.imo.backend.models.lessons.Lesson;
import com.imo.backend.models.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CreateCourseService {
    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    public CreateCourseService(
            CourseRepository courseRepository,
            UserRepository userRepository
    ) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    public CreateCourseResponse execute(CreateCourseRequest createCourseRequest) {

        Course potentialNewCourse = Course.fromCreateDto(createCourseRequest);

        List<Course> contributorCourses = courseRepository.findAllByContributor(potentialNewCourse.getContributor());

        boolean existingCourseName = contributorCourses.stream()
                .anyMatch(conflictCourse -> conflictCourse.getName().equals(potentialNewCourse.getName()));

        if (existingCourseName) {
            throw new ConflictException("Você ja cadastrou estre curso anteriormente");
        }

        List<Lesson> lessons = potentialNewCourse.getLessons();
        Set<List<String>> uniqueInfos = lessons.stream()
                .map(lesson -> Arrays.asList(lesson.getTitle(), lesson.getDescription(), lesson.getYoutubeLink()))
                .collect(Collectors.toSet());

        if (lessons.size() != uniqueInfos.size()) {
            throw new ConflictException("As informações e links de cadas aula do curso precisam ser diferentes uma das outras");
        }

        Course newCourse = courseRepository.save(potentialNewCourse);

        userRepository.updateContributionsByUsername(newCourse.getContributor(), newCourse);

        return new CreateCourseResponse("Aguarde sua contribuição ser validada", newCourse.getName(), newCourse.getContributor(), newCourse.getCategory());
    }
}
