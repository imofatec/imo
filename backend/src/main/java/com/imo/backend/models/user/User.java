package com.imo.backend.models.user;


import com.imo.backend.models.certificate.Certificate;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.CourseProgress;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("users")
@Data
public class User {

    @Id
    private String id;

    private String name;

    private String email;

    private String password;

    private String profilePicturePath;

    private List<Course> courseContributions = new ArrayList<>();

    private List<CourseProgress> coursesProgress = new ArrayList<>();

    private List<Certificate> certificates = new ArrayList<>();

    public User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }
}
