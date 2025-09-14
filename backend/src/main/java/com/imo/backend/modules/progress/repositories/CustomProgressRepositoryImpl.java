package com.imo.backend.modules.progress.repositories;

import com.imo.backend.modules.progress.Progress;
import com.imo.backend.modules.progress.ProgressDetails;
import com.imo.backend.utils.Pageable;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CustomProgressRepositoryImpl implements CustomProgressRepository {
  private final MongoTemplate mongoTemplate;

  public CustomProgressRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<Progress> findProgressByCourseId(String courseId, int page, int size) {
    Query query = new Query()
        .addCriteria(Criteria.where("courseId").is(new ObjectId(courseId)))
        .with(Pageable.fromPageSize(page, size));
    return this.mongoTemplate.find(query, Progress.class);
  }

  @Override
  public Optional<Progress> findByUserIdAndCourseId(String userId, String courseId) {
    Query query = new Query().addCriteria(Criteria
        .where("userId")
        .is(new ObjectId(userId))
        .and("courseId")
        .is(new ObjectId(courseId)));

    return Optional.ofNullable(mongoTemplate.findOne(query, Progress.class));
  }

  public List<ProgressDetails> findAllProgressDetailsByUserId(String userId) {
    Aggregation aggregation = buildAggregationProgressDetailsByUserId(userId, null, null);
    return mongoTemplate
        .aggregate(aggregation, "progress", ProgressDetails.class)
        .getMappedResults();
  }

  public List<ProgressDetails> findAllProgressDetailsByUserId(String userId, int page, int size) {
    Aggregation aggregation = buildAggregationProgressDetailsByUserId(userId, page, size);
    return mongoTemplate
        .aggregate(aggregation, "progress", ProgressDetails.class)
        .getMappedResults();
  }

  private Aggregation buildAggregationProgressDetailsByUserId(
      String userId,
      Integer page,
      Integer size
  ) {
    MatchOperation match = Aggregation.match(Criteria.where("userId").is(new ObjectId(userId)));
    LookupOperation lookupUsers = Aggregation.lookup("users", "userId", "_id", "user");

    Document matchLessonStage = new Document(
        "$match",
        new Document("$expr", new Document("$in", Arrays.asList("$_id", "$$lessonIds")))
    );
    List<Document> pipeline = Arrays.asList(matchLessonStage);

    Document lookupStage = new Document("from", "lessons")
        .append("let", new Document("lessonIds", "$lessonsWatched"))
        .append("pipeline", pipeline)
        .append("as", "lessons");
    AggregationOperation lookupLessons = context -> new Document("$lookup", lookupStage);

    LookupOperation lookupCourses = Aggregation.lookup(
        "courses",
        "lessons.courseId",
        "_id",
        "course"
    );

    ProjectionOperation projection = Aggregation
        .project()
        .andExclude("_id")
        .and(Aggregation.ROOT)
        .as("progress")
        .and("user")
        .as("user")
        .and("lessons")
        .as("lessons")
        .and("course")
        .as("course");

    AggregationOptions.Builder optionsBuilder = AggregationOptions.builder();

    Aggregation aggregation;

    if (page != null && size != null) {
      SortOperation sortOperation = Aggregation.sort(Sort.by(Sort.Direction.DESC, "createdAt"));
      aggregation = Aggregation.newAggregation(
          match,
          lookupUsers,
          Aggregation.unwind("user"),
          lookupLessons,
          lookupCourses,
          Aggregation.unwind("course"),
          sortOperation,
          Aggregation.skip((long) page * size),
          Aggregation.limit(size),
          projection
      );
    } else {
      aggregation = Aggregation.newAggregation(
          match,
          lookupUsers,
          Aggregation.unwind("user"),
          lookupLessons,
          lookupCourses,
          Aggregation.unwind("course"),
          projection
      );
    }

    return aggregation;
  }
}
