package com.imo.backend.models.course.services;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.ConflictException;
import com.imo.backend.models.category.Category;
import com.imo.backend.models.category.CategoryRepository;
import com.imo.backend.models.category.dtos.SummaryCourse;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.CreateCourseRequest;
import com.imo.backend.models.course.dtos.CreateCourseResponse;
import com.imo.backend.models.lessons.dtos.CreateLessonDto;
import com.imo.backend.models.user.UserRepository;
import com.imo.backend.utils.Slug;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CreateCourseService {
    private final CourseRepository courseRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final TokenService tokenService;

    public CreateCourseService(
            CourseRepository courseRepository,
            UserRepository userRepository,
            CategoryRepository categoryRepository, TokenService tokenService) {
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.tokenService = tokenService;
    }

    public CreateCourseResponse execute(CreateCourseRequest createCourseRequest, String token) {
        var contributor = tokenService.getSub(token);
        String contributorId = contributor.get("id");

        var slugVar = Slug.create(createCourseRequest.getName());

        List<Course> contributorCourses = courseRepository.findAllByContributorId(contributorId);
        checkConflictContributorCourse(contributorId, contributorCourses, slugVar);

        List<CreateLessonDto> lessons = createCourseRequest.getLessons();
        checkConflictLessons(lessons);

        Course potentialNewCourse = Course.fromCreateDto(createCourseRequest, contributor);

        Course newCourse = courseRepository.save(potentialNewCourse);
        var category = categoryRepository.findByName(newCourse.getCategory());

        SummaryCourse summaryCourse = SummaryCourse.fromCourse(newCourse);
        updateOrCreateCategory(category, newCourse, summaryCourse);

        userRepository.updateContributionsByUsername(newCourse.getContributorName(), newCourse);

        return new CreateCourseResponse("Aguarde sua contribuição ser validada", newCourse.getName(),
                newCourse.getContributorName(), newCourse.getCategory());
    }

    private static void checkConflictContributorCourse(String contributorId, List<Course> contributorCourses, String potentialNewCourseSlug) {

        boolean existingContributorCourse = contributorCourses.stream()
                .anyMatch(conflictCourse -> conflictCourse.getSlugCourse().equals(potentialNewCourseSlug));

        if (existingContributorCourse) {
            throw new ConflictException("Você ja cadastrou estre curso anteriormente");
        }
    }

    private static void checkConflictLessons(List<CreateLessonDto> lessons) {
        Set<List<String>> uniqueInfos = lessons.stream()
                .map(lesson -> Arrays.asList(lesson.getTitle(), lesson.getDescription(), lesson.getYoutubeLink()))
                .collect(Collectors.toSet());

        if (lessons.size() != uniqueInfos.size()) {
            throw new ConflictException(
                    "As informações e links de cadas aula do curso precisam ser diferentes uma das outras");
        }
    }


    private void updateOrCreateCategory(Optional<Category> category, Course newCourse, SummaryCourse summaryCourse) {
        if (category.isEmpty()) {
            Category newCategory = new Category();
            newCategory.setName(newCourse.getCategory());
            newCategory.setSlug(newCourse.getSlugCategory());
            newCategory.setCourses(summaryCourse);
            categoryRepository.save(newCategory);
        }

        if (category.isPresent()) {
            categoryRepository.updateCategoryByCourses(newCourse.getCategory(), summaryCourse);
        }
    }
}
