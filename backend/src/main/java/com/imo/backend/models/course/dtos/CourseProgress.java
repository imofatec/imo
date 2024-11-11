package com.imo.backend.models.course.dtos;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
public class CourseProgress {

    @Id
    private String id;

    private String name;

    private Integer totalLessons;

    private Integer lessonsWatched;

    private Status status;

    private LocalDateTime startedAt;

    private LocalDateTime finishedAt;

    public enum Status {
        FINISHED,
        IN_PROGRESS
    }
}
