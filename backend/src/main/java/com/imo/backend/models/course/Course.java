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

    private boolean active;

    private String name;

    private String category;

    private String contributorName;

    private String contributorId;

    private String description;

    private String slugCourse;

    private String slugCategory;

    @CreatedDate
    private LocalDateTime createdAt;

    private List<Lesson> lessons;

    private int totalLessons;
}
