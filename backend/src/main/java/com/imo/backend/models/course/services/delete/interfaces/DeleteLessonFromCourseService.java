package com.imo.backend.models.course.services.delete.interfaces;

public interface DeleteLessonFromCourseService {
    
    void execute(String token, String courseId, String lessonId);
}
