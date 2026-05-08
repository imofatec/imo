package com.imo.backend.contexts.catalog.course.repositories;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.catalog.course.CourseDetails;
import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.common.CombineWith;
import com.imo.backend.contexts.common.MatchType;
import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.common.Pageable;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

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
  public Optional<Course> findByIdAndIsActive(String id, boolean isActive) {
    Query query =
        new Query()
            .addCriteria(Criteria.where("_id").is(new ObjectId(id)).and("isActive").is(isActive));

    return Optional.ofNullable(this.mongoTemplate.findOne(query, Course.class));
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
      int page,
      int size,
      MatchType matchType,
      CombineWith combineWith,
      boolean isActive) {
    var filters = buildSearchFilters(searchParams, isActive);
    var pagination = Pageable.toMongodbAggregation(page, size);

    List<AggregationOperation> operations = new ArrayList<>();

    if (!filters.isEmpty()) {
      operations.add(MongoDB.buildMatchOperation(filters, matchType, combineWith));
    }

    operations.add(Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdAt")));
    operations.add(Aggregation.skip(pagination.get("skip")));
    operations.add(Aggregation.limit(pagination.get("limit")));

    return this.mongoTemplate
        .aggregate(Aggregation.newAggregation(operations), Course.class, Course.class)
        .getMappedResults();
  }

  @Override
  public long countSearch(
      CourseSearchParams searchParams,
      MatchType matchType,
      CombineWith combineWith,
      boolean isActive) {
    var filters = buildSearchFilters(searchParams, isActive);
    Query query = new Query();

    if (!filters.isEmpty()) {
      query.addCriteria(MongoDB.buildCombinedCriteria(filters, matchType, combineWith));
    }

    return this.mongoTemplate.count(query, Course.class);
  }

  @Override
  public List<CourseDetails> searchDetails(
      CourseSearchParams searchParams,
      int page,
      int size,
      MatchType matchType,
      CombineWith combineWith,
      boolean isActive) {
    Aggregation aggregation =
        buildCourseSearchDetailsAggregation(
            searchParams, matchType, combineWith, page, size, isActive);
    return this.mongoTemplate
        .aggregate(aggregation, "courses", CourseDetails.class)
        .getMappedResults();
  }

  @Override
  public CourseDetails findCourseDetailsByIdOrThrow(String id, boolean isActive) {
    return findCourseDetailsById(id, isActive);
  }

  private Aggregation buildCourseSearchDetailsAggregation(
      CourseSearchParams searchParams,
      MatchType matchType,
      CombineWith combineWith,
      Integer page,
      Integer size,
      Boolean isActive) {
    var filters = buildSearchFilters(searchParams, isActive);
    var pagination =
        page != null && size != null ? Pageable.toMongodbAggregation(page, size) : null;

    List<AggregationOperation> operations = new ArrayList<>();

    if (!filters.isEmpty()) {
      MatchOperation optionalMatchOperation =
          MongoDB.buildMatchOperation(filters, matchType, combineWith);
      operations.add(optionalMatchOperation);
    }

    operations.add(Aggregation.lookup("lessons", "_id", "courseId", "lessons"));

    ProjectionOperation project =
        Aggregation.project()
            .andExclude("_id")
            .and(Aggregation.ROOT)
            .as("course")
            .and("lessons")
            .as("lessons");

    if (page != null && size != null) {
      SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdAt"));
      operations.add(sortOperation);
      operations.add(Aggregation.skip(pagination.get("skip")));
      operations.add(Aggregation.limit(pagination.get("limit")));
    }

    operations.add(project);

    return Aggregation.newAggregation(operations);
  }

  private CourseDetails findCourseDetailsById(String id, Boolean isActive) {
    MongoDB.validateObjectId(id);

    Criteria criteria = Criteria.where("_id").is(new ObjectId(id));

    if (isActive != null) {
      criteria.and("isActive").is(isActive);
    }

    List<AggregationOperation> operations = new ArrayList<>();
    operations.add(Aggregation.match(criteria));
    operations.add(Aggregation.lookup("lessons", "_id", "courseId", "lessons"));

    ProjectionOperation project =
        Aggregation.project()
            .andExclude("_id")
            .and(Aggregation.ROOT)
            .as("course")
            .and("lessons")
            .as("lessons");

    operations.add(project);

    Aggregation aggregation = Aggregation.newAggregation(operations);
    var results =
        this.mongoTemplate
            .aggregate(aggregation, "courses", CourseDetails.class)
            .getMappedResults();

    if (results.isEmpty()) {
      throw new NotFoundException("Curso não Encontrado");
    }

    return results.get(0);
  }

  private java.util.Map<String, Object> buildSearchFilters(
      CourseSearchParams searchParams, Boolean isActive) {
    var filters = CourseSearchFilter.apply(searchParams);

    if (isActive != null) {
      filters.put("isActive", isActive);
    }

    return filters;
  }
}
