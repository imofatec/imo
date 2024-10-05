package com.imo.backend.models.user;

import com.imo.backend.models.course.Course;
import com.imo.backend.models.course.dtos.CourseProgress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);

    @Query("{ 'username':  ?0 }")
    @Update("{ '$push':  { 'courseContributions': ?1 } }")
    void updateContributionsByUsername(String username, Course course);

    @Query("{ 'id': ?0 }")
    @Update("{ $push: { coursesProgress: ?1 } }")
    void saveCourseProgressById(String id, CourseProgress progress);

    @Query("{ 'id':  ?0, 'coursesProgress.id': ?1, 'coursesProgress.lessonsWatched': { $lt: ?2 } }")
    @Update("{ $inc: { 'coursesProgress.$.lessonsWatched': 1 } }")
    void updateCourseProgressLessonsById(String id, String courseId, Integer totalLessons);

    @Query("{ 'id': ?0, 'coursesProgress.id': ?1 }")
    @Update("{ $set: { 'coursesProgress.$.status': ?2 } }")
    void updateCourseProgressStatusById(String id, String courseId, CourseProgress.Status status);
}
