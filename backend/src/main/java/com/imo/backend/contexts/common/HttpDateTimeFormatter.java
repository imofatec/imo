package com.imo.backend.contexts.common;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class HttpDateTimeFormatter {
  private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
  private static final DateTimeFormatter DATE_TIME_FORMATTER =
      DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

  private HttpDateTimeFormatter() {}

  public static String toDate(LocalDateTime value) {
    if (value == null) {
      return null;
    }

    return DATE_FORMATTER.format(value);
  }

  public static String toDateTime(LocalDateTime value) {
    if (value == null) {
      return null;
    }

    return DATE_TIME_FORMATTER.format(value);
  }
}
