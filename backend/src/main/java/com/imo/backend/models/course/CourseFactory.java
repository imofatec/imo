package com.imo.backend.models.course;

import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.dtos.CreateCourseRequest;
import com.imo.backend.models.lessons.Lesson;
import com.imo.backend.models.lessons.dtos.CreateLessonDto;
import com.imo.backend.utils.Slug;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

    public class CourseFactory {
        public static Course createCourse(
                CreateCourseRequest createCourseRequest,
                Map<String, String> contributor) {

            Course course = new Course();

            course.setActive(false);
            course.setContributorId(contributor.get("id"));
            course.setContributorName(contributor.get("username"));
            course.setName(createCourseRequest.getName());
            course.setSlugCourse(Slug.create(createCourseRequest.getName()));
            course.setCategory(createCourseRequest.getCategory());
            course.setSlugCategory(Slug.create(createCourseRequest.getCategory()));
            course.setDescription(createCourseRequest.getDescription());

            List<Lesson> formattedLessons = IntStream
                    .range(0, createCourseRequest.getLessons().size())
                    .mapToObj(i -> {
                        CreateLessonDto item = createCourseRequest.getLessons().get(i);
                        Lesson lesson = new Lesson();
                        lesson.setIndex(i + 1);
                        lesson.setTitle(item.getTitle());
                        lesson.setDescription(item.getDescription());
                        lesson.setYoutubeLink(item.getYoutubeLink());
                        lesson.setUploadedAt(LocalDateTime.now());
                        return lesson;
                    })
                    .collect(Collectors.toList());

            course.setLessons(formattedLessons);
            course.setTotalLessons(formattedLessons.size());

            return course;
        }

    public static CourseOverview createCourseOverview(Course course) {
        CourseOverview courseOverview = new CourseOverview();

        courseOverview.setId(course.getId());
        courseOverview.setActive(course.isActive());
        courseOverview.setName(course.getName());
        courseOverview.setSlugCourse(course.getSlugCourse());
        courseOverview.setCategory(course.getCategory());
        courseOverview.setSlugCategory(course.getSlugCategory());
        courseOverview.setDescription(course.getDescription());
        courseOverview.setFirstLessonYoutubeId(course.getLessons().get(0).getYoutubeLink());
        courseOverview.setTotalLessons(course.getTotalLessons());

        return courseOverview;
    }
}
