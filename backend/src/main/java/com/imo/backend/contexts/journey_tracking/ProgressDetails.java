package com.imo.backend.contexts.journey_tracking;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.identity.user.User;

import java.util.List;

public record ProgressDetails(
    Progress progress,
    User user,
    Course course,
    List<Lesson> lessons
) {
}
