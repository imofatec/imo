package com.imo.backend.contexts.catalog.course.repositories;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.CourseDetails;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.common.CombineWith;
import com.imo.backend.contexts.common.MatchType;
import com.imo.backend.contexts.common.MongoDB;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomCourseRepositoryImpl implements CustomCourseRepository {

  private final MongoTemplate mongoTemplate;

  public CustomCourseRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<Course> findAllByContributorId(String id) {
    Query query = new Query().addCriteria(Criteria.where("contributorId").is(new ObjectId(id)));

    return this.mongoTemplate.find(query, Course.class);
  }

  @Override
  public List<Course> findAllByContributorId(String id, Pageable page) {
    Query query = new Query()
        .addCriteria(Criteria.where("contributorId").is(new ObjectId(id)))
        .with(page);

    return this.mongoTemplate.find(query, Course.class);
  }

  @Override
  public Course toggleStatusById(String id, boolean currentStatus) {
    Query query = new Query(Criteria.where("_id").is(id));

    Update update = new Update().set("isActive", !currentStatus);

    return this.mongoTemplate.findAndModify(
        query,
        update,
        FindAndModifyOptions.options().returnNew(true),
        Course.class
    );
  }

  @Override
  public Course incLessonsCountById(String courseId) {
    Query query = new Query(Criteria.where("_id").is(courseId));
    Update update = new Update().inc("lessonsCount", 1);
    return mongoTemplate.findAndModify(
        query,
        update,
        FindAndModifyOptions.options().returnNew(true),
        Course.class
    );
  }

  @Override
  public Optional<Course> findByLessonId(String lessonId) {
    Lesson lesson = mongoTemplate.findById(lessonId, Lesson.class);

    if (lesson == null) {
      return Optional.empty();
    }

    Course course = mongoTemplate.findById(lesson.getCourseId(), Course.class);

    return Optional.ofNullable(course);
  }

  @Override
  public List<Course> search(
      CourseSearchParams searchParams,
      MatchType matchType,
      CombineWith combineWith
  ) {
    var filters = CourseSearchFilter.apply(searchParams);

    List<AggregationOperation> operations = new ArrayList<>();

    if (!filters.isEmpty()) {
      operations.add(MongoDB.buildMatchOperation(filters, matchType, combineWith));
    }

    operations.add(Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdAt")));

    return this.mongoTemplate
        .aggregate(Aggregation.newAggregation(operations), Course.class, Course.class)
        .getMappedResults();
  }

  @Override
  public List<Course> search(
      CourseSearchParams searchParams,
      int page,
      int size,
      MatchType matchType,
      CombineWith combineWith
  ) {
    var filters = CourseSearchFilter.apply(searchParams);

    List<AggregationOperation> operations = new ArrayList<>();

    if (!filters.isEmpty()) {
      operations.add(MongoDB.buildMatchOperation(filters, matchType, combineWith));
    }

    operations.add(Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdAt")));
    operations.add(Aggregation.skip((long) page * size));
    operations.add(Aggregation.limit(size));

    return this.mongoTemplate
        .aggregate(Aggregation.newAggregation(operations), Course.class, Course.class)
        .getMappedResults();
  }

  @Override
  public List<CourseDetails> searchDetails(
      CourseSearchParams searchParams,
      MatchType matchType,
      CombineWith combineWith
  ) {
    Aggregation aggregation = buildCourseSearchDetailsAggregation(
        searchParams,
        matchType,
        combineWith,
        null,
        null
    );
    return this.mongoTemplate
        .aggregate(aggregation, "courses", CourseDetails.class)
        .getMappedResults();
  }

  @Override
  public List<CourseDetails> searchDetails(
      CourseSearchParams searchParams,
      int page,
      int size,
      MatchType matchType,
      CombineWith combineWith
  ) {
    Aggregation aggregation = buildCourseSearchDetailsAggregation(
        searchParams,
        matchType,
        combineWith,
        page,
        size
    );
    return this.mongoTemplate
        .aggregate(aggregation, "courses", CourseDetails.class)
        .getMappedResults();
  }

  private Aggregation buildCourseSearchDetailsAggregation(
      CourseSearchParams searchParams,
      MatchType matchType,
      CombineWith combineWith,
      Integer page,
      Integer size
  ) {
    var filters = CourseSearchFilter.apply(searchParams);

    List<AggregationOperation> operations = new ArrayList<>();

    if (!filters.isEmpty()) {
      MatchOperation optionalMatchOperation = MongoDB.buildMatchOperation(
          filters,
          matchType,
          combineWith
      );
      operations.add(optionalMatchOperation);
    }

    operations.add(Aggregation.lookup("lessons", "_id", "courseId", "lessons"));

    ProjectionOperation project = Aggregation
        .project()
        .andExclude("_id")
        .and(Aggregation.ROOT)
        .as("course")
        .and("lessons")
        .as("lessons");

    if (page != null && size != null) {
      SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdAt"));
      operations.add(sortOperation);
      operations.add(Aggregation.skip((long) page * size));
      operations.add(Aggregation.limit(size));
    }

    operations.add(project);

    return Aggregation.newAggregation(operations);
  }

  @Override
  public List<Category> findAllCategories() {
    Aggregation aggregation = Aggregation.newAggregation(
        Aggregation.project("category"),
        Aggregation.replaceRoot("category"),
        Aggregation.group("slug", "name").first("slug").as("slug").first("name").as("name")
    );

    return mongoTemplate.aggregate(aggregation, "courses", Category.class).getMappedResults();
  }


  @Override
  public List<Category> findAllCategories(int page, int size) {
    SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdAt"));

    Aggregation aggregation = Aggregation.newAggregation(
        sortOperation,
        Aggregation.project("category"),
        Aggregation.replaceRoot("category"),
        Aggregation.group("slug", "name").first("slug").as("slug").first("name").as("name"),
        Aggregation.skip((long) page * size),
        Aggregation.limit(size)
    );

    return mongoTemplate.aggregate(aggregation, "courses", Category.class).getMappedResults();
  }
}
