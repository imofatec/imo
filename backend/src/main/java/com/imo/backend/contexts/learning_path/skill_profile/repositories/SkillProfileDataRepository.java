package com.imo.backend.contexts.learning_path.skill_profile.repositories;

import com.imo.backend.contexts.catalog.skill.Skill;
import com.imo.backend.contexts.common.exceptions.custom.NotFoundException;
import com.imo.backend.contexts.identity.user.User;
import com.imo.backend.contexts.learning_path.skill_profile.CourseSkillsDetails;
import com.imo.backend.contexts.learning_path.skill_profile.gateways.SkillProfileDataGateway;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;

@Repository
public class SkillProfileDataRepository implements SkillProfileDataGateway {
  private final MongoTemplate mongoTemplate;

  public SkillProfileDataRepository(MongoTemplate mongoTemplate) {
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
  public List<Skill> findSkillsByCourseIdOrThrow(String courseId) {
    AggregationOperation match =
        Aggregation.match(Criteria.where("_id").is(new ObjectId(courseId)));
    AggregationOperation lookupSkills = Aggregation.lookup("skills", "skillIds", "_id", "skills");
    ProjectionOperation project =
        Aggregation.project().and(Aggregation.ROOT).as("course").and("skills").as("skills");

    CourseSkillsDetails courseSkillsDetails =
        this.mongoTemplate
            .aggregate(
                Aggregation.newAggregation(match, lookupSkills, project),
                "courses",
                CourseSkillsDetails.class)
            .getUniqueMappedResult();

    if (courseSkillsDetails == null || courseSkillsDetails.course() == null) {
      throw new NotFoundException("Curso não Encontrado");
    }

    List<Skill> skills = courseSkillsDetails.skills();

    if (skills == null
        || skills.size() != courseSkillsDetails.course().getSkillIdsAsString().size()) {
      throw new NotFoundException("Uma ou mais skills do curso não foram encontradas");
    }

    return skills;
  }
}
