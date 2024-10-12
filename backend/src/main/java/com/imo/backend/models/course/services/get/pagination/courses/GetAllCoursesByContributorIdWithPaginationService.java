package com.imo.backend.models.course.services.get.pagination.courses;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.strategy.get.many.pagination.GetManyByToListWithPaginationService;
import com.imo.backend.lib.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetAllCoursesByContributorIdWithPaginationService
        implements GetManyByToListWithPaginationService<Course> {

    private final TokenService tokenService;

    private final CourseRepository courseRepository;

    public GetAllCoursesByContributorIdWithPaginationService(
            TokenService tokenService,
            CourseRepository courseRepository
    ) {
        this.tokenService = tokenService;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> execute(String token, Integer page, Integer size) {
        var pageable = Pageable.fromPageSize(page, size);

        var userId = tokenService.getSub(token).get("id");

        return courseRepository.findAllByContributorId(userId, pageable).getContent();
    }
}
