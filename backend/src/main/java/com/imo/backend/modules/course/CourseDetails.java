package com.imo.backend.modules.course;

import com.imo.backend.modules.lesson.Lesson;

import java.util.List;

public record CourseDetails(
    Course course,
    List<Lesson> lessons
) {
}
