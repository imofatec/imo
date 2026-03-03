package com.imo.backend.contexts.identity.value_objects;

import lombok.Getter;

@Getter
public enum ExperienceLevel {
  BEGINNER("Iniciante"),
  INTERMEDIATE("Intermediário"),
  ADVANCED("Avançado"),
  EXPERT("Especialista");

  private final String value;

  ExperienceLevel(String value) {
    this.value = value;
  }
}
