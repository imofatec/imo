package com.imo.backend.contexts.common.exceptions.custom;

public class UnauthorizedException extends RuntimeException {
  public UnauthorizedException(String message) {
    super(message);
  }
}
