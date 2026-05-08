package com.imo.backend.contexts.learning_path.recommendation.repositories;

import com.imo.backend.contexts.catalog.course.Course;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.journey_tracking.progress.Progress;
import com.imo.backend.contexts.journey_tracking.progress.value_objects.ProgressStatus;
import com.imo.backend.contexts.learning_path.recommendation.RecommendationCourseDetails;
import com.imo.backend.contexts.learning_path.recommendation.RecommendationSkillDetails;
import com.imo.backend.contexts.learning_path.recommendation.gateways.RecommendationDataGateway;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public class RecommendationDataRepository implements RecommendationDataGateway {
  private final MongoTemplate mongoTemplate;

  public RecommendationDataRepository(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public void assertUserExists(String userId) {
    User user = this.mongoTemplate.findById(new ObjectId(userId), User.class);

    if (user == null) {
      throw new NotFoundException("Usuário não encontrado");
    }
  }

  @Override
  public Set<String> findFinishedCourseIdsByUserId(String userId) {
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
        .collect(Collectors.toSet());
  }

  @Override
  public List<Course> findActiveCoursesByIds(List<String> courseIds) {
    if (courseIds == null || courseIds.isEmpty()) {
      return List.of();
    }

    Query query =
        new Query()
            .addCriteria(
                Criteria.where("_id")
                    .in(courseIds.stream().map(ObjectId::new).toList())
                    .and("isActive")
                    .is(true));

    return this.mongoTemplate.find(query, Course.class);
  }

  @Override
  public List<String> findLatestActiveCourseIdsExcluding(
      List<String> excludedCourseIds, int limit) {
    Query query =
        new Query()
            .addCriteria(Criteria.where("isActive").is(true))
            .with(Sort.by(Sort.Direction.DESC, "createdAt"));
    query.fields().include("_id");

    if (excludedCourseIds != null && !excludedCourseIds.isEmpty()) {
      query.addCriteria(
          Criteria.where("_id").nin(excludedCourseIds.stream().map(ObjectId::new).toList()));
    }

    if (limit > 0) {
      query.limit(limit);
    }

    return this.mongoTemplate.find(query, Course.class).stream().map(Course::getId).toList();
  }

  @Override
  public List<RecommendationCourseDetails> findRecommendationCandidateCourseDetails(
      Set<String> knownSkillIds, Set<String> excludedCourseIds) {
    if (knownSkillIds == null || knownSkillIds.isEmpty()) {
      return List.of();
    }

    Query query =
        new Query()
            .addCriteria(
                Criteria.where("isActive")
                    .is(true)
                    .and("skillIds")
                    .in(knownSkillIds.stream().map(ObjectId::new).toList()));
    query.fields().include("_id").include("skillIds");

    if (excludedCourseIds != null && !excludedCourseIds.isEmpty()) {
      query.addCriteria(
          Criteria.where("_id").nin(excludedCourseIds.stream().map(ObjectId::new).toList()));
    }

    return this.mongoTemplate.find(query, RecommendationCourseDetails.class, "courses");
  }

  @Override
  public Map<String, RecommendationSkillDetails> findRecommendationSkillDetailsByIds(
      Set<String> skillIds) {
    if (skillIds == null || skillIds.isEmpty()) {
      return Map.of();
    }

    Query query =
        new Query()
            .addCriteria(Criteria.where("_id").in(skillIds.stream().map(ObjectId::new).toList()));
    query.fields().include("_id").include("isEssential");

    return this.mongoTemplate.find(query, RecommendationSkillDetails.class, "skills").stream()
        .collect(Collectors.toMap(RecommendationSkillDetails::id, Function.identity()));
  }
}
