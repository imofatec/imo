package com.imo.backend.models.course.repositories;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.FieldsToUpdateCourse;
import com.imo.backend.models.lessons.Lesson;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CustomCourseRepositoryImpl implements CustomCourseRepository {

    private final MongoTemplate mongoTemplate;

    public CustomCourseRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Course updateCourse(String token, String courseId, FieldsToUpdateCourse fieldsToUpdateCourse) {

        Course existingCourse = findCourseById(courseId);
        if (existingCourse == null) {
            return null;
        }

        Query query = createQueryForCourseUpdate(token, courseId);
        Update update = new Update();
        updateBasicCourseFields(update, fieldsToUpdateCourse);

        if (fieldsToUpdateCourse.getLessons() != null && !fieldsToUpdateCourse.getLessons().isEmpty()) {
            updateCourseLessons(update, existingCourse.getLessons(), fieldsToUpdateCourse.getLessons());
        }

        update.currentDate("updatedAt");

        mongoTemplate.updateFirst(query, update, Course.class);

        return mongoTemplate.findById(courseId, Course.class);
    }

    private Course findCourseById(String courseId) {
        return mongoTemplate.findById(courseId, Course.class);
    }

    private Query createQueryForCourseUpdate(String token, String courseId) {
        return new Query(Criteria.where("_id").is(courseId)
                .and("contributorId").is(token));
    }

    private void updateBasicCourseFields(Update update, FieldsToUpdateCourse fieldsToUpdateCourse) {
        if (fieldsToUpdateCourse.getName() != null && !fieldsToUpdateCourse.getName().trim().isEmpty()) {
            update.set("name", fieldsToUpdateCourse.getName());
        }

        if (fieldsToUpdateCourse.getLevel() != null && !fieldsToUpdateCourse.getLevel().trim().isEmpty()) {
            update.set("level", fieldsToUpdateCourse.getLevel());
        }

        if (fieldsToUpdateCourse.getCategory() != null && !fieldsToUpdateCourse.getCategory().trim().isEmpty()) {
            update.set("category", fieldsToUpdateCourse.getCategory());
        }

        if (fieldsToUpdateCourse.getDescription() != null && !fieldsToUpdateCourse.getDescription().trim().isEmpty()) {
            update.set("description", fieldsToUpdateCourse.getDescription());
        }
    }

    private void updateCourseLessons(Update update, List<Lesson> existingLessons, List<Lesson> newLessons) {
        List<Lesson> updatedLessons = processLessonsUpdate(existingLessons, newLessons);
        update.set("lessons", updatedLessons);
        update.set("totalLessons", updatedLessons.size());
    }

    private List<Lesson> processLessonsUpdate(List<Lesson> existingLessons, List<Lesson> newLessons) {
        List<Lesson> updatedLessons = new ArrayList<>();

        for (int i = 0; i < newLessons.size(); i++) {
            Lesson newLesson = newLessons.get(i);
            newLesson.setIndex(i);

            findAndPreserveLessonId(newLesson, existingLessons, i);

            updatedLessons.add(newLesson);
        }
        return updatedLessons;
    }

    private void findAndPreserveLessonId(Lesson newLesson, List<Lesson> existingLessons, int position) {
        if (existingLessons == null || existingLessons.isEmpty()) {
            return;
        }

        Lesson existingLesson = position < existingLessons.size()
                ? existingLessons.get(position)
                : existingLessons.stream()
                        .filter(lesson -> lesson.getTitle() != null)
                        .filter(lesson -> lesson.getTitle().equals(newLesson.getTitle()))
                        .findFirst()
                        .orElse(null);

        if (existingLesson != null && existingLesson.getId() != null) {
            newLesson.setId(existingLesson.getId());
            preserveExistingFieldsIfNewAreEmpty(newLesson, existingLesson);
        }
    }

    private void preserveExistingFieldsIfNewAreEmpty(Lesson newLesson, Lesson existingLesson) {
        if (existingLesson == null)
            return;

        if (newLesson.getTitle() == null || newLesson.getTitle().trim().isEmpty()) {
            newLesson.setTitle(existingLesson.getTitle());
        }

        if (newLesson.getDescription() == null || newLesson.getDescription().trim().isEmpty()) {
            newLesson.setDescription(existingLesson.getDescription());
        }

        if (newLesson.getYoutubeLink() == null || newLesson.getYoutubeLink().trim().isEmpty()) {
            newLesson.setYoutubeLink(existingLesson.getYoutubeLink());
        }

        if (existingLesson.getComments() != null && !existingLesson.getComments().isEmpty()) {
            newLesson.setComments(existingLesson.getComments());
        }
    }

}
