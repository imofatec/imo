package com.imo.backend.models.course.services.delete;

import com.imo.backend.exceptions.custom.NotFoundException;
import com.imo.backend.models.course.CourseRepository;
import com.imo.backend.models.course.services.delete.interfaces.DeleteLessonFromCourseService;
import com.imo.backend.models.lessons.dtos.NoCommentsLesson;
import org.springframework.stereotype.Service;

@Service
public class DeleteLessonFromCourseServiceImpl implements DeleteLessonFromCourseService {

    private final CourseRepository courseRepository;

    public DeleteLessonFromCourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public NoCommentsLesson execute(String lessonId) {
            var existingCourse = courseRepository.findByLessonId(lessonId);

            if (existingCourse == null) {
              throw new NotFoundException("Aula não encontrada");
            }

            var existingLesson = existingCourse.getLessons().stream()
                .filter(lesson -> lesson.getId().equals(lessonId))
                .findFirst();

            assert existingLesson.isPresent();

            var updatedCourse = courseRepository.deleteLessonFromCourse(existingLesson.get().getId());

            if (updatedCourse == null) {
                return null;
            }

            var deletedLesson = updatedCourse.getLessons().stream()
                .filter(lesson -> lesson.getId().equals(lessonId)).findFirst().orElse(null);

            return deletedLesson != null ? new NoCommentsLesson(deletedLesson) : null;
    }

}
