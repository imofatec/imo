package com.imo.backend.models.course.services.delete;

import org.springframework.stereotype.Service;
import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.ForbiddenException;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.services.delete.interfaces.DeleteLessonFromCourseService;

@Service
public class DeleteLessonFromCourseServiceImpl implements DeleteLessonFromCourseService {

    private final CourseRepository courseRepository;
    private final TokenService tokenService;

    public DeleteLessonFromCourseServiceImpl(CourseRepository courseRepository, TokenService tokenService) {
        this.courseRepository = courseRepository;
        this.tokenService = tokenService;
    }

    @Override
    public void execute(String token, String courseId, String lessonId) {
       
        String userId = tokenService.getSub(token).get("id");

            Course existingCourse = courseRepository.findById(courseId)
                    .orElseThrow(() -> {
                        return new NotFoundException("Curso não encontrado");
                    });

            if (!existingCourse.getContributorId().equals(userId)) {
                throw new ForbiddenException("Você não tem permissão para atualizar este curso");
            }

            if (existingCourse.getLessons().stream().noneMatch(lesson -> lesson.getId().equals(lessonId))) {
                throw new NotFoundException("Aula não encontrada no curso");
            }

            courseRepository.deleteLessonFromCourse(userId, courseId, lessonId);
    }

}
