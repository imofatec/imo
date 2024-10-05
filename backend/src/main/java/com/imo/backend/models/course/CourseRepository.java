package com.imo.backend.models.course;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends MongoRepository<Course, String> {
    Optional<Course> findBySlugCourse(String slugCourse);


    List<Course> findAllBySlugCategory(String slugCategory);

    Page<Course> findAllBySlugCategory(String slugCategory, Pageable pageable);

    List<Course> findAllByContributorId(String id);
}
