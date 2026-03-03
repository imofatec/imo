package com.imo.backend.contexts.identity.value_objects;

import lombok.Getter;

@Getter
public enum AvailableTimePerDay {
  LESS_THAN_ONE_HOUR("Menos de 1 hora por dia"),
  ONE_TO_TWO_HOURS("Entre 1 a 2 horas por dia"),
  TWO_TO_FOUR_HOURS("Entre 1 a 4 horas por dia"),
  MORE_THAN_FOUR_HOURS("Mais de 4 horas por dia");

  private final String value;

  AvailableTimePerDay(String value) {
    this.value = value;
  }
}
