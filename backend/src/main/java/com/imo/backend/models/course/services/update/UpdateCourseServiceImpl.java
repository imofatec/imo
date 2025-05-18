package com.imo.backend.models.course.services.update;


import org.springframework.stereotype.Service;
import com.imo.backend.config.token.TokenService;
import com.imo.backend.exceptions.custom.ForbiddenException;
import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.dtos.FieldsToUpdateCourse;
import com.imo.backend.models.course.services.update.interfaces.UpdateCourseService;

@Service
public class UpdateCourseServiceImpl implements UpdateCourseService {

    private final CourseRepository courseRepository;
    private final TokenService tokenService;

    public UpdateCourseServiceImpl(CourseRepository courseRepository, TokenService tokenService) {
        this.tokenService = tokenService;
        this.courseRepository = courseRepository;
    }

    public Course execute(String token, String courseId, FieldsToUpdateCourse fieldsToUpdateCourse) {
        
        String userId = tokenService.getSub(token).get("id");

            Course existingCourse = courseRepository.findById(courseId)
                    .orElseThrow(() -> {
                        return new NotFoundException("Curso não encontrado");
                    });

            if (!existingCourse.getContributorId().equals(userId)) {
                throw new ForbiddenException("Você não tem permissão para atualizar este curso");
            }
            courseRepository.updateCourse(userId, courseId, fieldsToUpdateCourse);

            Course updatedCourse = courseRepository.findById(courseId)
                    .orElseThrow(() -> {
                        return new NotFoundException("Erro ao recuperar o curso atualizado");
                    });
            
            return updatedCourse;
            
    }
}
