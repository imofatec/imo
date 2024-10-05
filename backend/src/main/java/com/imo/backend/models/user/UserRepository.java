package com.imo.backend.models.user;

import com.imo.backend.models.course.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByUsername(String username);
    Optional<User> findById(String id);

    @Query("{'username':  ?0}")
    @Update("{'$push':  {'contributions': ?1} }")
    void updateContributionsByUsername(String username, Course course);
}
