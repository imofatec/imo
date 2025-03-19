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

    Optional<User> findByName(String name);


    @Query("{ 'name':  ?0 }")
    @Update("{ '$push':  { 'courseContributions': ?1 } }")
    void updateContributionsByName(String name, Course course);

    @Query("{ '_id': ?0 }")
    @Update("{ $push: { coursesProgress: ?1 } }")
    void saveCourseProgressById(String id, CourseProgress progress);

    @Query("{ '_id':  ?0, 'coursesProgress._id': ?1 }")
    @Update("{ $inc: { 'coursesProgress.$.lessonsWatched': 1 } }")
    void updateCourseProgressLessonsById(String id, String courseId);

    @Query("{ '_id': ?0, 'coursesProgress._id': ?1 }")
    @Update("{ $set: { 'coursesProgress.$.status': ?2, 'coursesProgress.$.finishedAt': ?3 }}")
    void updateCourseProgressStatusById(String id, String courseId, CourseProgress.Status status, LocalDateTime dateTime);

    @Query("{ '_id': ?0 }")
    @Update("{ $set: { 'profilePicturePath': ?1 } }")
    void updateProfilePictureById(String id, String profilePicturePath);

    @Query("{ '_id': ?0 }")
    @Update("{ $push: { 'certificates': ?1 } }")
    void pushCertificateById(String userId, Certificate certificate);

    @Query("{ '_id': ?0, 'certificates._id': ?1 }")
    @Update("{ $set: { 'certificates.$.issuedAt': ?2} }")
    void updateIssuedAtCertificateById(String userId, String certificateId, LocalDateTime localDateTime);

    @Query("{ '_id' : ?0 }")
    @Update("{ $set: { 'isConfirmed':?1 }}")
    void updateUserAccessById(String userId, Boolean isConfirmed);
}
