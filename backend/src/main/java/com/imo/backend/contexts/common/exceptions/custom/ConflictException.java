package com.imo.backend.contexts.common.exceptions.custom;

public class ConflictException extends RuntimeException {
  public ConflictException(String message) {
    super(message);
  }
}
