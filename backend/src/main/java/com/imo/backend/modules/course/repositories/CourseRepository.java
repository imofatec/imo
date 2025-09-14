package com.imo.backend.modules.course.repositories;

import com.imo.backend.modules.course.Course;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String>, CustomCourseRepository {
}
