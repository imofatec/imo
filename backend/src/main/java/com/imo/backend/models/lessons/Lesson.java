package com.imo.backend.models.lessons;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document("lessons")
@Getter
@Setter
public class Lesson {

    @Id
    private String id;

    private String title;

    private String description;

    private String youtubeLink;

    @CreatedDate
    private LocalDateTime uploadedAt;

}
