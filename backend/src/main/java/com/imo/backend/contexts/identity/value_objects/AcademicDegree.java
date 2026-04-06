package com.imo.backend.contexts.identity.value_objects;

import lombok.Getter;

@Getter
public enum AcademicDegree {
  NONE("Sem escolaridade"),
  TECHNICAL("Ensino técnico"),
  ASSOCIATE("Técnologo"),
  BACHELOR("Bacharelado"),
  LICENTIATE("Licenciatura"),
  MBA("MBA"),
  MASTER("Mestrado"),
  DOCTORAL("Doutorado"),
  POSTDOC("Pós-doutorado");

  private final String value;

  AcademicDegree(String value) {
    this.value = value;
  }
}
