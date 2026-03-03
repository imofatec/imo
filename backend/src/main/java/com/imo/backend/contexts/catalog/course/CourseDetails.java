package com.imo.backend.contexts.catalog.course;

import com.imo.backend.contexts.catalog.lesson.Lesson;

import java.util.List;

public record CourseDetails(
    Course course,
    List<Lesson> lessons
) {
}
