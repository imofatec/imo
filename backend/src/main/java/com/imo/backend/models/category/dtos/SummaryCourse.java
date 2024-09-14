package com.imo.backend.models.category.dtos;

import com.imo.backend.models.course.Course;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
public class SummaryCourse {

    private String id;

    private boolean active;

    private String name;

    private String description;

    private LocalDateTime createdAt;

    public static SummaryCourse fromCourse(Course course) {
        SummaryCourse summaryCourse = new SummaryCourse();

        summaryCourse.setId(course.getId());
        summaryCourse.setActive(course.isActive());
        summaryCourse.setName(course.getName());
        summaryCourse.setDescription(course.getDescription());
        summaryCourse.setCreatedAt(course.getCreatedAt());

        return summaryCourse;
    }
}
