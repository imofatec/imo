package com.imo.backend.contexts.journey_tracking.repositories;

import com.imo.backend.contexts.common.Pageable;
import com.imo.backend.contexts.journey_tracking.Progress;
import com.imo.backend.contexts.journey_tracking.ProgressDetails;
import com.imo.backend.contexts.journey_tracking.value_objects.ProgressStatus;
import com.imo.backend.contexts.recognition.gateways.AchievementMetricsGateway;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.Size;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class CustomProgressRepositoryImpl
    implements CustomProgressRepository, AchievementMetricsGateway {
  private final MongoTemplate mongoTemplate;

  public CustomProgressRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<Progress> findProgressByCourseId(String courseId, int page, int size) {
    Query query =
        new Query()
            .addCriteria(Criteria.where("courseId").is(new ObjectId(courseId)))
            .with(Pageable.fromPageSize(page, size));
    return this.mongoTemplate.find(query, Progress.class);
  }

  @Override
  public Optional<Progress> findByUserIdAndCourseId(String userId, String courseId) {
    Query query =
        new Query()
            .addCriteria(
                Criteria.where("userId")
                    .is(new ObjectId(userId))
                    .and("courseId")
                    .is(new ObjectId(courseId)));

    return Optional.ofNullable(mongoTemplate.findOne(query, Progress.class));
  }

  @Override
  public Optional<ProgressDetails> findDetailsByUserIdAndCourseId(String userId, String courseId) {
    MatchOperation match =
        Aggregation.match(
            new Criteria()
                .andOperator(
                    Criteria.where("userId")
                        .is(new ObjectId(userId))
                        .and("courseId")
                        .is(new ObjectId(courseId))));

    Aggregation aggregation = this.buildAggregationProgressDetailsByUserId(match, null, null);

    var results =
        this.mongoTemplate
            .aggregate(aggregation, "progress", ProgressDetails.class)
            .getMappedResults();

    return Optional.ofNullable(results.isEmpty() ? null : results.getFirst());
  }

  public List<ProgressDetails> findAllProgressDetailsByUserId(String userId, int page, int size) {
    MatchOperation match = Aggregation.match(Criteria.where("userId").is(new ObjectId(userId)));

    Aggregation aggregation = buildAggregationProgressDetailsByUserId(match, page, size);
    return mongoTemplate
        .aggregate(aggregation, "progress", ProgressDetails.class)
        .getMappedResults();
  }

  @Override
  public List<String> findFinishedCourseIdsByUserId(String userId) {
    Query query =
        new Query()
            .addCriteria(
                Criteria.where("userId")
                    .is(new ObjectId(userId))
                    .and("status")
                    .is(ProgressStatus.FINISHED));
    query.fields().include("courseId").exclude("_id");

    return this.mongoTemplate.find(query, Progress.class).stream()
        .map(Progress::getCourseId)
        .toList();
  }

  @Override
  public long countAllProgressDetailsByUserId(String userId) {
    Query query = new Query().addCriteria(Criteria.where("userId").is(new ObjectId(userId)));
    return this.mongoTemplate.count(query, Progress.class);
  }

  private Aggregation buildAggregationProgressDetailsByUserId(
      MatchOperation matchOperaion, Integer page, Integer size) {
    List<AggregationOperation> operations = new ArrayList<>();
    LookupOperation lookupUsers = Aggregation.lookup("users", "userId", "_id", "user");

    Document matchLessonStage =
        new Document(
            "$match",
            new Document("$expr", new Document("$in", Arrays.asList("$_id", "$$lessonIds"))));
    List<Document> pipeline = Arrays.asList(matchLessonStage);

    Document lookupStage =
        new Document("from", "lessons")
            .append("let", new Document("lessonIds", "$lessonsWatched"))
            .append("pipeline", pipeline)
            .append("as", "lessons");
    AggregationOperation lookupLessons = context -> new Document("$lookup", lookupStage);

    LookupOperation lookupCourses =
        Aggregation.lookup("courses", "lessons.courseId", "_id", "course");

    ProjectionOperation projection =
        Aggregation.project()
            .andExclude("_id")
            .and(Aggregation.ROOT)
            .as("progress")
            .and("user")
            .as("user")
            .and("lessons")
            .as("lessons")
            .and("course")
            .as("course");

    operations.add(matchOperaion);
    operations.add(lookupUsers);
    operations.add(Aggregation.unwind("user"));
    operations.add(lookupLessons);
    operations.add(lookupCourses);
    operations.add(Aggregation.unwind("course"));

    if (page != null && size != null) {
      SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "updatedAt"));
      operations.add(sortOperation);
      operations.add(Aggregation.skip((long) page * size));
      operations.add(Aggregation.limit(size));
    }

    operations.add(projection);

    return Aggregation.newAggregation(operations);
  }

  @Override
  public long countFinishedCoursesByUserId(String userId) {
    Query query =
        new Query()
            .addCriteria(
                Criteria.where("userId")
                    .is(new ObjectId(userId))
                    .and("status")
                    .is(ProgressStatus.FINISHED));

    return this.mongoTemplate.count(query, Progress.class);
  }

  @Override
  public long countWatchedLessonsByUserId(String userId) {
    MatchOperation match = Aggregation.match(Criteria.where("userId").is(new ObjectId(userId)));
    ProjectionOperation project =
        Aggregation.project().and(Size.lengthOfArray("lessonsWatched")).as("lessonsWatchedCount");
    GroupOperation group = Aggregation.group().sum("lessonsWatchedCount").as("total");

    Aggregation aggregation = Aggregation.newAggregation(match, project, group);

    Document result =
        this.mongoTemplate
            .aggregate(aggregation, "progress", Document.class)
            .getUniqueMappedResult();

    if (result == null) {
      return 0;
    }

    return result.get("total", 0);
  }
}
