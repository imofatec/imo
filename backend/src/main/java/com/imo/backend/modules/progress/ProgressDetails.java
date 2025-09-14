package com.imo.backend.modules.progress;

import com.imo.backend.modules.course.Course;
import com.imo.backend.modules.lesson.Lesson;
import com.imo.backend.modules.user.User;

import java.util.List;

public record ProgressDetails(
    Progress progress,
    User user,
    Course course,
    List<Lesson> lessons
) {
}
