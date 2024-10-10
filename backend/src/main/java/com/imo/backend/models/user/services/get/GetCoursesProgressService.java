package com.imo.backend.models.user.services.get;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.dtos.CourseProgress;
import com.imo.backend.models.strategy.get.many.to_list.GetManyByToListService;
import com.imo.backend.models.user.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetCoursesProgressService implements GetManyByToListService<CourseProgress> {
    private final TokenService tokenService;

    private final UserRepository userRepository;

    public GetCoursesProgressService(
            TokenService tokenService,
            UserRepository userRepository
    ) {
        this.tokenService = tokenService;
        this.userRepository = userRepository;
    }

    @Override
    public List<CourseProgress> execute(String token) {
        var userId = tokenService.getSub(token).get("id");
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado"))
                .getCoursesProgress();
    }
}
