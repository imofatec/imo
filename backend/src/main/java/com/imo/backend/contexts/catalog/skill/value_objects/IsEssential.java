package com.imo.backend.contexts.catalog.skill.value_objects;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;

public record IsEssential(int value) {
  public IsEssential {
    if (value < 0 || value > 3) {
      throw new BadRequestException("O valor de isEssential deve estar entre 0 e 3");
    }
  }
}
