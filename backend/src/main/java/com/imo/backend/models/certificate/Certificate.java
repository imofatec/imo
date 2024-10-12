package com.imo.backend.models.certificate;

import com.imo.backend.models.course.Course;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Document(collection = "certificates")
@Data
public class Certificate {

    private String id;

    private String userId;

    private String username;

    private String userEmail;

    private String courseId;

    private String courseName;

    private LocalDateTime courseStartDate;

    private LocalDateTime courseEndDate;

    private LocalDateTime issuedAt;

}
