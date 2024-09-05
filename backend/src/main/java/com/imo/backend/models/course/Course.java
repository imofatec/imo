package com.imo.backend.models.course;

import com.imo.backend.models.lessons.Lesson;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("courses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    private String id;

    private String name;
    private String contributor;
    private String category;
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    private List<Lesson> lessons;


}
