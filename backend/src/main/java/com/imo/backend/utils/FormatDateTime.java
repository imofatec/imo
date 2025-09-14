package com.imo.backend.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FormatDateTime {
  public static String toDate(LocalDateTime localDateTime) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    return dateTimeFormatter.format(localDateTime);
  }

  public static String toDateTime(LocalDateTime localDateTime) {
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    return dateTimeFormatter.format(localDateTime);
  }
}
