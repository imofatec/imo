package com.imo.backend.models.user.dtos;

import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.dtos.CourseProgress;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserCourseOverview {
    private CourseProgress.Status status;

    private Integer lessonsWatched;

    private CourseOverview courseOverview;

    public static UserCourseOverview fromCourseProgressCourseOverview(
            CourseProgress courseProgress,
            CourseOverview courseOverview
    ) {
        return new UserCourseOverview(
                courseProgress.getStatus(),
                courseProgress.getLessonsWatched(),
                courseOverview
        );
    }
}
