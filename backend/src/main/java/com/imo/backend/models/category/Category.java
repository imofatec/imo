package com.imo.backend.models.category;

import com.imo.backend.models.category.dtos.SummaryCourse;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("categories")
@Setter
public class Category {
    @Id
    String id;

    String name;

    String slug;

    List<SummaryCourse> courses = new ArrayList<>();

    public void setCourses(SummaryCourse course) {
        this.courses.add(course);
    }

}
