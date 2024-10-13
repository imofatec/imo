package com.imo.backend.models.course.services.create;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.ConflictException;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.CreateCourseRequest;
import com.imo.backend.models.course.dtos.CreateCourseResponse;
import com.imo.backend.models.lessons.dtos.CreateLessonDto;
import com.imo.backend.models.strategy.create.CreateWithTokenService;
import com.imo.backend.models.user.User;
import com.imo.backend.models.user.repositories.UserRepository;
import com.imo.backend.lib.Slug;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CreateCourseService implements CreateWithTokenService<CreateCourseRequest, CreateCourseResponse> {
    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final TokenService tokenService;

    public CreateCourseService(
            CourseRepository courseRepository,
            UserRepository userRepository,
            TokenService tokenService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.tokenService = tokenService;
    }

    @Override
    public CreateCourseResponse execute(CreateCourseRequest createCourseRequest, String token) {
        var contributor = tokenService.getSub(token);
        String contributorId = contributor.get("id");

        var contributorCourses = courseRepository.findAllByContributorId(contributorId);
        var user = userRepository.findById(contributorId)
                .orElseThrow(() -> new NotFoundException("Usuário autenticado não encontrado"));
        var potentialNewSlugCourse = Slug.create(createCourseRequest.getName());
        checkConflictContributorCourse(contributorCourses, user, potentialNewSlugCourse);

        var lessons = createCourseRequest.getLessons();
        checkConflictLessons(lessons);

        var course = CourseFactory.createCourse(createCourseRequest, contributor);
        Course newCourse = courseRepository.save(course);

        userRepository.updateContributionsByUsername(newCourse.getContributorName(), newCourse);

        return new CreateCourseResponse("Aguarde sua contribuição ser validada", newCourse.getName(),
                newCourse.getContributorName(), newCourse.getCategory());
    }

    private static void checkConflictContributorCourse(
            List<Course> contributorCourses,
            User user,
            String potentialNewSlugCourse) {

        boolean existingContributorCourse = contributorCourses.stream()
                .anyMatch(course -> course.getSlugCourse().equals(potentialNewSlugCourse));

        boolean existingContributorCoursePt2 = user.getCourseContributions().stream()
                .anyMatch(course -> course.getSlugCourse().equals(potentialNewSlugCourse));

        if (existingContributorCourse || existingContributorCoursePt2) {
            throw new ConflictException(
                    String.format("Você ja cadastrou o curso %s anteriormente", potentialNewSlugCourse)
            );
        }
    }

    private static void checkConflictLessons(List<CreateLessonDto> lessons) {
        var titles = new HashSet<>();
        var descriptions = new HashSet<>();
        var youtubeLinks = new HashSet<>();

        lessons.forEach(lesson -> {
            if (!titles.add(lesson.getTitle())) {
                throw new ConflictException(
                        String.format("Titulo '%s' repetido", lesson.getTitle())
                );
            }

            if (!descriptions.add(lesson.getDescription())) {
                throw new ConflictException(
                        String.format("Descrição '%s...' repetida", lesson.getDescription().substring(10))
                );
            }

            if (!youtubeLinks.add(lesson.getYoutubeLink())) {
                throw new ConflictException(
                        String.format("Aula '%s' repetida", lesson.getYoutubeLink()));
            }
        });
    }
}
