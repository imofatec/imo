package com.imo.backend.models.course.services.delete.interfaces;

import com.imo.backend.models.lessons.dtos.NoCommentsLesson;

public interface DeleteLessonFromCourseService {
    
    NoCommentsLesson execute(String lessonId);
}
