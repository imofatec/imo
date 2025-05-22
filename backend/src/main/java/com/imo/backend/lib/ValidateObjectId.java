package com.imo.backend.lib;

import com.imo.backend.exceptions.custom.BadRequestException;
import org.bson.types.ObjectId;

public class ValidateObjectId {
  public static void execute(String id) {
    if (!ObjectId.isValid(id)) {
      throw new BadRequestException("Id inválido");
    }
  }
}
