package com.imo.backend.models.user.services.get;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.dtos.CourseProgress;
import com.imo.backend.models.strategy.get.one.GetOneByWithTokenService;
import com.imo.backend.models.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class GetCourseProgressByIdService implements GetOneByWithTokenService<CourseProgress> {
    private final TokenService tokenService;

    private final UserRepository userRepository;

    public GetCourseProgressByIdService(
            TokenService tokenService,
            UserRepository userRepository
    ) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    public CourseProgress execute(String courseId, String token) {
        var userId = tokenService.getSub(token).get("id");
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"))
                .getCoursesProgress().stream()
                .filter(courseProgress -> courseProgress.getId().equals(courseId))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Curso %s não encontrado", courseId)));
    }
}
