package com.imo.backend.models.course.dtos;

import lombok.Data;

@Data
public class CourseOverview {

    private String id;

    private boolean active;

    private String name;

    private String slugCourse;

    private String category;

    private String slugCategory;

    private String description;

    private String firstLessonYoutubeId;

    private int totalLessons;
}
