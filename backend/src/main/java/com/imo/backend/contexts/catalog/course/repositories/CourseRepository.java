package com.imo.backend.contexts.catalog.course.repositories;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;

import java.util.Optional;
import java.util.List;
import com.imo.backend.contexts.common.Pageable;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends MongoRepository<Course, String>, CustomCourseRepository {

    List<Course> findAllByContributorId(String contributorId);
    List<Course> findAllByContributorId(String contributorId, Pageable pageable);


    Optional<Course> findByLessonId(String lessonId);

    default Course findByIdOrThrow(String id) {
        return this.findById(id)
        .orElseThrow(() -> new NotFoundException("Curso não Encontrado"));
    }

    default Course findByLessonIdOrThrow(String lessonId) {
        return this.findByLessonId(lessonId)
        .orElseThrow(() -> new NotFoundException("Lição não Encontrada"));
    }

}
