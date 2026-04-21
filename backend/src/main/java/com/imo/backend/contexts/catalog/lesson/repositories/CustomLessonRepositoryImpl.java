package com.imo.backend.contexts.catalog.lesson.repositories;

import com.imo.backend.contexts.catalog.lesson.Lesson;
import com.imo.backend.contexts.common.CombineWith;
import com.imo.backend.contexts.common.MatchType;
import com.imo.backend.contexts.common.MongoDB;
import com.imo.backend.contexts.common.Pageable;
import java.util.List;
import java.util.Map;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
public class CustomLessonRepositoryImpl implements CustomLessonRepository {
  private final MongoTemplate mongoTemplate;

  public CustomLessonRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<Lesson> findAllByCourseId(String courseId) {
    Query query = new Query(Criteria.where("courseId").is(new ObjectId(courseId)));
    return this.mongoTemplate.find(query, Lesson.class);
  }

  @Override
  public List<Lesson> search(
      LessonSearchParams searchParams,
      int page,
      int size,
      MatchType matchType,
      CombineWith combineWith) {
    var filters = LessonSearchFilter.apply(searchParams);
    var pagination = Pageable.toMongodbAggregation(page, size);
    List<AggregationOperation> operations = buildSearchAggregation(filters, matchType, combineWith);

    operations.add(Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdAt")));
    operations.add(Aggregation.skip(pagination.get("skip")));
    operations.add(Aggregation.limit(pagination.get("limit")));

    return this.mongoTemplate
        .aggregate(Aggregation.newAggregation(operations), "lessons", Lesson.class)
        .getMappedResults();
  }

  @Override
  public long countSearch(
      LessonSearchParams searchParams, MatchType matchType, CombineWith combineWith) {
    var filters = LessonSearchFilter.apply(searchParams);
    List<AggregationOperation> operations = buildSearchAggregation(filters, matchType, combineWith);
    operations.add(Aggregation.count().as("total"));

    var results =
        this.mongoTemplate
            .aggregate(Aggregation.newAggregation(operations), "lessons", Document.class)
            .getMappedResults();

    if (results.isEmpty()) {
      return 0;
    }

    return ((Number) results.getFirst().get("total")).longValue();
  }

  private List<AggregationOperation> buildSearchAggregation(
      Map<String, Object> filters, MatchType matchType, CombineWith combineWith) {
    List<AggregationOperation> operations = new java.util.ArrayList<>();
    operations.add(Aggregation.lookup("courses", "courseId", "_id", "course"));
    operations.add(Aggregation.unwind("course"));

    if (!filters.isEmpty()) {
      operations.add(MongoDB.buildMatchOperation(filters, matchType, combineWith));
    }

    return operations;
  }
}
