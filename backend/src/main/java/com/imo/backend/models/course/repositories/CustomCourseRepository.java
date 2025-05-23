package com.imo.backend.models.course.repositories;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.FieldsToUpdateCourse;
import com.imo.backend.models.course.dtos.FieldsToUpdateLesson;
import com.imo.backend.models.lessons.dtos.CreateLessonDto;

import java.util.List;

public interface CustomCourseRepository {

    Course updateCourseById(String courseId, FieldsToUpdateCourse fieldsToUpdateCourse);

    Course updateLessonById(String lessonId, FieldsToUpdateLesson fieldsToUpdateLesson);

    Course pushLesson(String courseId, List<CreateLessonDto> lessonsDto);

    Course updateCourseStatus(String courseId, boolean isActive);

    Course deleteLessonFromCourse(String lessonId);
}