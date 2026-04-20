package com.imo.backend.contexts.catalog.skill;

import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.catalog.course.value_objects.Category;
import com.imo.backend.contexts.catalog.skill.value_objects.IsEssential;
import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

@EqualsAndHashCode(callSuper = true)
@Document("skills")
@Data
public class Skill extends Entity {
  private Category category;

  private String name;

  private IsEssential isEssential;

  public Skill() {}

  public Skill(Categories category, String name, int isEssential) {
    this.setCategory(new Category(category));
    this.setName(name);
    this.setIsEssential(new IsEssential(isEssential));
  }

  public void setName(String name) {
    if (name == null || name.trim().isEmpty()) {
      throw new BadRequestException("O nome da skill é obrigatório");
    }
    this.name = name.trim();
  }
}
