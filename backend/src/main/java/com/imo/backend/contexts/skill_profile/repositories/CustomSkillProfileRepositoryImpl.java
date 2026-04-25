package com.imo.backend.contexts.skill_profile.repositories;

import com.imo.backend.contexts.skill_profile.SkillProfileDetails;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;

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
}
