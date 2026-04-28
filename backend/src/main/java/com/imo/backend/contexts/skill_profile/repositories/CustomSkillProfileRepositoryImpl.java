package com.imo.backend.contexts.skill_profile.repositories;

import com.imo.backend.contexts.skill_profile.SkillProfile;
import com.imo.backend.contexts.skill_profile.SkillProfileDetails;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class CustomSkillProfileRepositoryImpl implements CustomSkillProfileRepository {
  private final MongoTemplate mongoTemplate;

  public CustomSkillProfileRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<SkillProfileDetails> findAllDetailsByUserId(String userId) {
    AggregationOperation match =
        Aggregation.match(Criteria.where("userId").is(new ObjectId(userId)));
    AggregationOperation lookupSkills = Aggregation.lookup("skills", "skillId", "_id", "skill");
    AggregationOperation unwindSkill = Aggregation.unwind("skill");
    ProjectionOperation project =
        Aggregation.project()
            .andExclude("_id")
            .and(Aggregation.ROOT)
            .as("skillProfile")
            .and("skill")
            .as("skill");

    Aggregation aggregation = Aggregation.newAggregation(match, lookupSkills, unwindSkill, project);

    return this.mongoTemplate
        .aggregate(aggregation, "skill_profile", SkillProfileDetails.class)
        .getMappedResults();
  }

  @Override
  public List<String> findDistinctUserIdsBySkillIds(List<String> skillIds) {
    if (skillIds == null || skillIds.isEmpty()) {
      return List.of();
    }

    Query query =
        new Query()
            .addCriteria(
                Criteria.where("skillId").in(skillIds.stream().map(ObjectId::new).toList()));
    query.fields().include("userId").exclude("_id");

    return this.mongoTemplate.find(query, SkillProfile.class).stream()
        .map(SkillProfile::getUserId)
        .distinct()
        .collect(Collectors.toList());
  }
}
