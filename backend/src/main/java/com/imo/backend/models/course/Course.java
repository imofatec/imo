package com.imo.backend.models.course;

import com.imo.backend.models.course.dtos.CreateCourseRequest;
import com.imo.backend.models.lessons.Lesson;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document("courses")
@NoArgsConstructor
@Getter
@Setter
public class Course {

    @Id
    private String id;

    private boolean active;

    @Size(min = 10, max = 50, message = "O curso precisa ter um nome entre 10 e 50 caracteres")
    private String name;

    private String contributor;

    private String category;

    private String description;


    @CreatedDate
    private LocalDateTime createdAt;

    private List<Lesson> lessons;

    public static Course fromCreateDto(CreateCourseRequest createCourseRequest) {
        Course course = new Course();
        course.setActive(false);
        course.setName(createCourseRequest.getName());
        course.setContributor(createCourseRequest.getContributor());
        course.setCategory(createCourseRequest.getCategory());
        course.setDescription(createCourseRequest.getDescription());
        course.setLessons(createCourseRequest.getLessons());

        List<Lesson> formattedLessons = course.getLessons();

        for (int i = 0; i < formattedLessons.size(); i++) {
            var item = formattedLessons.get(i);
            item.setIndex(i + 1);
            item.formatLink();
            item.setUploadedAt(LocalDateTime.now());
        }

        course.setLessons(formattedLessons);

        return course;
    }

}
