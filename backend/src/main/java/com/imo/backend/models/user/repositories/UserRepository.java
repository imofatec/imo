package com.imo.backend.models.user.repositories;

import com.imo.backend.models.certificate.Certificate;
import com.imo.backend.models.course.Course;
import com.imo.backend.models.user.User;
import com.imo.backend.models.course.dtos.CourseProgress;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String>, CustomUserRepository {
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
    @Update("{ $set: { 'coursesProgress.$.status': ?2, 'coursesProgress.$.finishedAt': ?3 }}")
    void updateCourseProgressStatusById(String id, String courseId, CourseProgress.Status status, LocalDateTime dateTime);

    @Query("{ 'id': ?0 }")
    @Update("{ $set: { 'profilePicturePath': ?1 } }")
    void updateProfilePictureById(String id, String profilePicturePath);

    @Query("{ 'id': ?0 }")
    @Update("{ $push: { 'certificates': ?1 } }")
    void pushCertificateById(String userId, Certificate certificate);

    @Query("{ 'id': ?0, 'certificates.id': ?1 }")
    @Update("{ $set: { 'certificates.$.issuedAt': ?2} }")
    void updateIssuedAtCertificateById(String userId, String certificateId, LocalDateTime localDateTime);


}
