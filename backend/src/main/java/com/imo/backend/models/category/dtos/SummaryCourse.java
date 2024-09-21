package com.imo.backend.models.category.dtos;

import com.imo.backend.models.course.Course;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SummaryCourse {

    private String id;

    private boolean active;

    private String name;

    private String description;

    private String firstLessonYoutubeId;

    private LocalDateTime createdAt;

    public static SummaryCourse fromCourse(Course course) {
        SummaryCourse summaryCourse = new SummaryCourse();

        summaryCourse.setId(course.getId());
        summaryCourse.setActive(course.isActive());
        summaryCourse.setName(course.getName());
        summaryCourse.setDescription(course.getDescription());
        summaryCourse.setFirstLessonYoutubeId(course.getLessons().getFirst().getYoutubeLink());
        summaryCourse.setCreatedAt(course.getCreatedAt());

        return summaryCourse;
    }
}
