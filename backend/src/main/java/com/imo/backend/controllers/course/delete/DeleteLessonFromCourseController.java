package com.imo.backend.controllers.course.delete;

import com.imo.backend.config.token.TokenService;
import com.imo.backend.controllers.course.CourseController;
import com.imo.backend.models.course.services.authorization.interfaces.ValidateUserCourseAccessService;
import com.imo.backend.models.course.services.delete.interfaces.DeleteLessonFromCourseService;
import com.imo.backend.models.course.services.get.courses.interfaces.GetCourseByLessonIdService;
import com.imo.backend.models.lessons.dtos.NoCommentsLesson;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeleteLessonFromCourseController extends CourseController {

    private final DeleteLessonFromCourseService deleteLessonFromCourseService;

    private final TokenService tokenService;

    private final ValidateUserCourseAccessService validateUserCourseAccessService;

    private final GetCourseByLessonIdService getCourseByLessonIdService;
    public DeleteLessonFromCourseController(DeleteLessonFromCourseService deleteLessonFromCourseService,
        TokenService tokenService, ValidateUserCourseAccessService validateUserCourseAccessService,
        GetCourseByLessonIdService getCourseByLessonIdService
    ) {
        this.deleteLessonFromCourseService = deleteLessonFromCourseService;
      this.tokenService = tokenService;
      this.validateUserCourseAccessService = validateUserCourseAccessService;
      this.getCourseByLessonIdService = getCourseByLessonIdService;

    }

    @Operation(summary = "Delete Lesson from Course")
    @SecurityRequirement(name = "Authorization")
    @DeleteMapping("/lessons/{lessonId}")
    public ResponseEntity<NoCommentsLesson> handle(HttpServletRequest request, @PathVariable String lessonId) {
        var userId = this.tokenService.getSub(request.getHeader("Authorization")).get("id");

        var existingCourse = this.getCourseByLessonIdService.execute(lessonId);

        this.validateUserCourseAccessService.execute(userId, existingCourse.getId());

        var deletedLesson = deleteLessonFromCourseService.execute(lessonId);
        return deletedLesson != null
            ? ResponseEntity.ok(deletedLesson)
            : ResponseEntity.noContent().build();
    }       

}

