package com.imo.backend.models.category;

import com.imo.backend.models.category.dtos.SummaryCourse;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String> {
    Optional<Category> findByName(String name);

    @Query("{'name': ?0}")
    @Update("{'$push': {'courses':  ?1}}")
    void updateCategoryByCourses(String name, SummaryCourse course);
}
