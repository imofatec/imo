package com.imo.backend.models.course;

import com.imo.backend.models.comments.Comment;
import com.imo.backend.models.course.repositories.CustomCourseRepository;

import org.bson.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends MongoRepository<Course, String>, CustomCourseRepository{
    Optional<Course> findBySlugCourse(String slugCourse);

    List<Course> findAllBySlugCategory(String slugCategory);

    List<Course> findAllBySlugLevel(String slugLevel);

    Page<Course> findAllBySlugLevel(String slugLevel, Pageable pageable);

    Page<Course> findAllBySlugCategory(String slugCategory, Pageable pageable);

    Course findByContributorId(String contributorId);

    List<Course> findAllByContributorId(String id);

    Page<Course> findAllByContributorId(String id, Pageable page);

    @Query("{ 'name': { $regex: ?0, $options: 'i' } }")
    Page<Course> findAllByName(String name, Pageable page);


    @Query("{ '_id': ?0, 'lessons.id': ?1 }")
    @Update("{ $push: { 'lessons.$.comments': ?2 } }")
    long addCommentToLesson(String courseId, String lessonId, Comment comment);

    @Aggregation(pipeline = {
        "{ $match: { '_id': ?0 } }",
        "{ $unwind: '$lessons' }",
        "{ $match: { 'lessons.id': ?1 } }",
        "{ $unwind: '$lessons.comments' }",
        "{ $replaceRoot: { newRoot: '$lessons.comments' } }"
    })
    List<Comment> findCommentsByLessonId(String courseId,String lessonId);


    @Query("{'lessons_id': ?0}")
    Course findByLessonId(String lessonId);

}
