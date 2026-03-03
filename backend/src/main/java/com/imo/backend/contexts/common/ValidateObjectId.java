package com.imo.backend.contexts.common;

import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import org.bson.types.ObjectId;

public record ValidateObjectId() {
  public static void execute(String id) {
    if (!ObjectId.isValid(id)) {
      throw new BadRequestException("Id inválido");
    }
  }
}
