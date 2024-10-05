package com.imo.backend.models.course.dtos;

import lombok.Data;

@Data
public class CourseProgress {
    private String id;

    private String name;

    private Integer totalLessons;

    private Integer lessonsWatched;

    private Status status;

    public enum Status {
        FINISHED,
        IN_PROGRESS
    }
}
