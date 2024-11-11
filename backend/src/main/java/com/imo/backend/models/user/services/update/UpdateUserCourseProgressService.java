package com.imo.backend.models.user.services.update;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseFactory;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.CourseProgress;
import com.imo.backend.models.strategy.update.UpdateByIdService;
import com.imo.backend.models.user.User;
import com.imo.backend.models.user.repositories.UserRepository;
import com.imo.backend.models.user.dtos.UserCourseProgress;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
public class UpdateUserCourseProgressService implements UpdateByIdService<Void, UserCourseProgress> {
    private final UserRepository userRepository;

    private final TokenService tokenService;

    private final CourseRepository courseRepository;

    public UpdateUserCourseProgressService(
            UserRepository userRepository,
            TokenService tokenService,
            CourseRepository courseRepository
    ) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.courseRepository = courseRepository;
    }

    @Transactional
    public UserCourseProgress execute(String courseId, Void dto, String token) {
        var userId = tokenService.getSub(token).get("id");
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Uusário não encontrado"));
        var course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Curso não encontrado"));

        CourseProgress currentProgress = checkCurrentUserCourseProgress(course, user);

        if (currentProgress == null) {
            var courseProgress = CourseFactory.createCourseProgress(course);
            courseProgress.setStartedAt(LocalDateTime.now());
            userRepository.saveCourseProgressById(userId, courseProgress);
            return updatedUserCourseProgress(userRepository, userId, courseId);
        }

        if (currentProgress.getStatus() == CourseProgress.Status.FINISHED) {
            return updatedUserCourseProgress(userRepository, userId, courseId);
        }

        userRepository.updateCourseProgressLessonsById(userId, courseId);
        var updatedUser = updatedUserCourseProgress(userRepository, userId, courseId);
        var updatedUserCoursesProgress = updatedUser.courseProgress();

        if (Objects.equals(
                updatedUserCoursesProgress.getTotalLessons(),
                updatedUserCoursesProgress.getLessonsWatched())
        ) {
            userRepository.updateCourseProgressStatusById(userId, courseId, CourseProgress.Status.FINISHED, LocalDateTime.now());
            return updatedUserCourseProgress(userRepository, userId, courseId);
        }

        return updatedUser;
    }

    private static CourseProgress checkCurrentUserCourseProgress(Course course, User user) {
        return user.getCoursesProgress()
                .stream()
                .filter(courseProgress -> courseProgress.getId().equals(course.getId()))
                .findFirst()
                .orElse(null);
    }

    private static UserCourseProgress updatedUserCourseProgress(UserRepository userRepository, String userId, String courseId) {
        var user = userRepository.findById(userId).get();
        var currentProgress = user
                .getCoursesProgress()
                .stream()
                .filter(course -> course.getId().equals(courseId))
                .findFirst().get();

        return new UserCourseProgress(
                user.getId(),
                user.getName(),
                user.getEmail(),
                currentProgress);
    }
}
