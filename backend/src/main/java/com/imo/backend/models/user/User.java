package com.imo.backend.models.user;


import com.imo.backend.models.course.Course;
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

    private String username;

    private String email;

    private String password;

    private List<Course> contributions;

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {
    }

}
