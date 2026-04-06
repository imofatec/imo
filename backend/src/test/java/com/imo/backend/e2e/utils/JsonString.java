package com.imo.backend.e2e.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonString {
  private ObjectMapper objectMapper = new ObjectMapper();

  public String fromObj(Object obj) {
    try {
      return this.objectMapper.writeValueAsString(obj);
    } catch (JsonProcessingException e) {
      throw new RuntimeException(e);
    }
  }
}
