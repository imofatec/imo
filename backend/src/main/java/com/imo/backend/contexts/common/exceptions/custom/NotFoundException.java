package com.imo.backend.contexts.common.exceptions.custom;

public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}
