package com.imo.backend.contexts.catalog.skill.repositories;

import com.imo.backend.contexts.catalog.skill.Skill;
import java.util.List;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

public class CustomSkillRepositoryImpl implements CustomSkillRepository {
  private final MongoTemplate mongoTemplate;

  public CustomSkillRepositoryImpl(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  @Override
  public List<Skill> findAllByCategorySlug(String categorySlug) {
    Query query = new Query().addCriteria(Criteria.where("category.slug").is(categorySlug));

    return this.mongoTemplate.find(query, Skill.class);
  }
}
