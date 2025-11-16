package com.imo.backend.modules.certificate.repositories;

import com.imo.backend.modules.certificate.CertificateDetails;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CustomCertificateRepositoryImpl implements CustomCertificateRepository {
  private final MongoTemplate mongoTemplate;

  public CustomCertificateRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public Optional<CertificateDetails> findDetailsByUserIdAndCourseId(
      String userId,
      String courseId
  ) {
    MatchOperation matchOperation = new MatchOperation(Criteria
        .where("userId")
        .is(new ObjectId(userId))
        .and("courseId")
        .is(new ObjectId(courseId)));

    return this.findCertificateDetails(matchOperation);
  }

  @Override
  public Optional<CertificateDetails> findDetailsById(String id) {
    MatchOperation matchOperation = new MatchOperation(Criteria.where("_id").is(new ObjectId(id)));

    return this.findCertificateDetails(matchOperation);
  }

  private Optional<CertificateDetails> findCertificateDetails(MatchOperation matchOperation) {
    LookupOperation lookupUser = Aggregation.lookup("users", "userId", "_id", "user");
    LookupOperation lookupCourse = Aggregation.lookup("courses", "courseId", "_id", "course");

    ProjectionOperation project = Aggregation
        .project()
        .andExclude("_id")
        .and(Aggregation.ROOT)
        .as("certificate")
        .and("user")
        .as("user")
        .and("course")
        .as("course");

    Aggregation pipeline = Aggregation.newAggregation(
        matchOperation,
        lookupUser,
        Aggregation.unwind("user"),
        lookupCourse,
        Aggregation.unwind("course"),
        project
    );

    AggregationResults<CertificateDetails> results = this.mongoTemplate.aggregate(
        pipeline,
        "certificates",
        CertificateDetails.class
    );

    return Optional.ofNullable((!results.getMappedResults().isEmpty()) ? results
        .getMappedResults()
        .getFirst() : null);
  }

}
