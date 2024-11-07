package com.imo.backend.models.course.services.get.pagination.course_overviews;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.dtos.CourseProgress;
import com.imo.backend.models.strategy.get.many.pagination.GetManyByToListWithPaginationService;
import com.imo.backend.models.user.repositories.UserRepository;
import com.imo.backend.models.user.dtos.UserCourseOverview;
import com.imo.backend.lib.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class GetAllCourseOverviewsByUserCourseProgressesWithPaginationService
        implements GetManyByToListWithPaginationService<UserCourseOverview> {

    private final UserRepository userRepository;

    private final CourseRepository courseRepository;

    private final TokenService tokenService;

    public GetAllCourseOverviewsByUserCourseProgressesWithPaginationService(
            UserRepository userRepository,
            CourseRepository courseRepository,
            TokenService tokenService
    ) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.tokenService = tokenService;
    }

    @Override
    public List<UserCourseOverview> execute(String token, Integer page, Integer size) {
        var userId = tokenService.getSub(token).get("id");

        List<CourseProgress> userCoursesProgress = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"))
                .getCoursesProgress();

        Set<String> userCoursesIds = userCoursesProgress.stream()
                .map(CourseProgress::getId)
                .collect(Collectors.toSet());

        var pageable = Pageable.fromPageSize(page, size);
        List<Course> courses = courseRepository.findAll(pageable).stream().toList();

        List<Course> matchedCourses = courses.stream()
                .filter(course -> userCoursesIds.contains(course.getId()))
                .toList();

        List<CourseOverview> matchedCourseOverviews = matchedCourses.stream()
                .map(CourseFactory::createCourseOverview)
                .toList();

        List<UserCourseOverview> userCourseOverviews = IntStream.range(0, matchedCourseOverviews.size())
                .mapToObj(i -> UserCourseOverview.fromCourseProgressCourseOverview(
                        userCoursesProgress.get(i),
                        matchedCourseOverviews.get(i)
                ))
                .toList();

        return userCourseOverviews;
    }
}
