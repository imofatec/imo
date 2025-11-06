package com.imo.backend.modules.user.value_objects;

import lombok.Getter;

@Getter
public enum AcademicDegree {
  NONE("Sem escolaridade"),
  TECHNICAL("Ensino técnico"),
  ASSOCIATE("Técnologo"),
  BACHELOR("Bacharelado"),
  LICENTIATE("Licenciatura"),
  LATO_SENSU("Especialização lato sensu"),
  MASTER("Mestrado"),
  DOCTORAL("Doutorado"),
  POSTDOCTORATE("Pós-doutorado");


  private final String value;

  AcademicDegree(String value) {
    this.value = value;
  }
}
