package com.imo.backend.models.course;

import com.imo.backend.models.lessons.Lesson;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("courses")
@Data
public class Course {

    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdAt;

    private boolean active;

    private String contributorId;

    private String contributorName;

    private String name;

    private String slugCourse;

    private String category;

    private String slugCategory;

    private String description;

    private int totalLessons;

    private List<Lesson> lessons;
}
