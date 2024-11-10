package com.imo.backend.models.course;

import com.imo.backend.exceptions.custom.BadRequestException;
import com.imo.backend.models.certificate.Certificate;
import com.imo.backend.models.course.dtos.CourseOverview;
import com.imo.backend.models.course.dtos.CourseProgress;
import com.imo.backend.models.course.dtos.CreateCourseRequest;
import com.imo.backend.models.lessons.Lesson;
import com.imo.backend.models.lessons.dtos.CreateLessonDto;
import com.imo.backend.models.user.User;
import com.imo.backend.lib.Slug;
import org.bson.types.ObjectId;

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
        course.setContributorName(contributor.get("name"));
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

    public static CourseProgress createCourseProgress(Course course) {
        CourseProgress courseProgress = new CourseProgress();

        courseProgress.setId(course.getId());
        courseProgress.setName(course.getName());
        courseProgress.setTotalLessons(course.getTotalLessons());
        courseProgress.setLessonsWatched(0);
        courseProgress.setStatus(CourseProgress.Status.IN_PROGRESS);

        return courseProgress;
    }

    public static Certificate createCertificate(User user, Course course) {
        Certificate certificate = new Certificate();

        certificate.setId(new ObjectId().toString());
        certificate.setUserId(user.getId());
        certificate.setName(user.getName());
        certificate.setUserEmail(user.getEmail());
        certificate.setCourseId(course.getId());
        certificate.setCourseName(course.getName());
        certificate.setCourseSlug(course.getSlugCourse());

        var courseProgress = user.getCoursesProgress().stream()
                .filter(data -> data.getId().equals(course.getId()))
                .findFirst()
                .orElseThrow(() -> new BadRequestException(
                        String.format("Curso %s não iniciado", course.getName())
                ));

        if (!courseProgress.getStatus().equals(CourseProgress.Status.FINISHED)) {
            throw new BadRequestException(
                    String.format("Curso %s não finalizado", course.getName()));
        }

        certificate.setCourseStartedAt(courseProgress.getStartedAt());
        certificate.setCourseFinishedAt(courseProgress.getFinishedAt());
        certificate.setIssuedAt(LocalDateTime.now());

        return certificate;
    }
}
