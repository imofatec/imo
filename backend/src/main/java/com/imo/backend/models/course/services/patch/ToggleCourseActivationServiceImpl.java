package com.imo.backend.models.course.services.patch;

import org.springframework.stereotype.Service;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.ForbiddenException;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.services.patch.interfaces.ToggleCourseActivationService;

@Service
public class ToggleCourseActivationServiceImpl implements ToggleCourseActivationService {

    private final CourseRepository courseRepository;
    private final TokenService tokenService;

    public ToggleCourseActivationServiceImpl(CourseRepository courseRepository, TokenService tokenService) {
        this.courseRepository = courseRepository;
        this.tokenService = tokenService;
    }

    @Override
    public void execute(String token, String courseId, boolean isActive) {

        String userId = tokenService.getSub(token).get("id");

        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    return new NotFoundException("Curso não encontrado");
                });

        if (!existingCourse.getContributorId().equals(userId)) {
            throw new ForbiddenException("Você não tem permissão para atualizar este curso");
        }

        courseRepository.updateCourseStatus(userId, courseId, isActive);
    }

    @Override
    public void activate(String token, String courseId) {
        execute(token, courseId, true);
    }

    @Override
    public void deactivate(String token, String courseId) {
        execute(token, courseId, false);
    }

    @Override
    public void toggle(String token, String courseId) {
        String userId = tokenService.getSub(token).get("id");

        Course existingCourse = courseRepository.findById(courseId)
                .orElseThrow(() -> {
                    return new NotFoundException("Curso não encontrado");
                });

        if (!existingCourse.getContributorId().equals(userId)) {
            throw new ForbiddenException("Você não tem permissão para atualizar este curso");
        }

        boolean newStatus = !existingCourse.isActive();
        courseRepository.updateCourseStatus(userId, courseId, newStatus);
    }
}
