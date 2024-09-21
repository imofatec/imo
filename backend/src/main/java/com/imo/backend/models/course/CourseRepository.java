package com.imo.backend.models.course;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    List<Course> findAllByCategory(String category);
    List<Course> findAllByContributorId(String id);
}
