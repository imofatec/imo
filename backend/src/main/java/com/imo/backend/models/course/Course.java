package com.imo.backend.models.course;

import com.imo.backend.models.course.dtos.CreateCourseRequest;
import com.imo.backend.models.lessons.Lesson;
import com.imo.backend.models.lessons.dtos.CreateLessonDto;
import com.imo.backend.utils.Slug;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Document("courses")
@Getter
@Setter
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


    public static Course fromCreateDto(CreateCourseRequest createCourseRequest, Map<String, String> contributor) {
        Course course = new Course();
        course.setActive(false);
        course.setName(createCourseRequest.getName());
        course.setCategory(createCourseRequest.getCategory());
        course.setContributorId(contributor.get("id"));
        course.setContributorName(contributor.get("username"));
        course.setDescription(createCourseRequest.getDescription());
        course.setSlugCourse(Slug.create(createCourseRequest.getName()));
        course.setSlugCategory(Slug.create(createCourseRequest.getCategory()));

        List<CreateLessonDto> createLessonDto = createCourseRequest.getLessons();

        List<Lesson> formattedLessons = new ArrayList<>();

        for (int i = 0; i < createLessonDto.size(); i++) {
            var item = createLessonDto.get(i);

            Lesson lesson = new Lesson();

            lesson.setIndex(i + 1);
            lesson.setTitle(item.getTitle());
            lesson.setDescription(item.getDescription());
            lesson.setYoutubeLink(item.getYoutubeLink());
            lesson.setUploadedAt(LocalDateTime.now());

            lesson.formatLink();

            formattedLessons.add(lesson);

        }

        course.setLessons(formattedLessons);
        course.setTotalLessons(formattedLessons.size());

        return course;
    }
}
