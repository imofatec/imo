package com.imo.backend.contexts.catalog.course.value_objects;

import java.util.List;
import lombok.Getter;

@Getter
public enum Categories {
  AI("Inteligência artificial"),
  DATA("Dados"),
  CLOUD("Computação em nuvem"),
  DEV_WEB("Desenvolvimento web"),
  SECURITY("Segurança"),
  DEV_MOBILE("Desenvolvimento mobile");

  private final String value;

  Categories(String value) {
    this.value = value;
  }

  public static List<Categories> getAll() {
    return List.of(
        Categories.AI,
        Categories.DATA,
        Categories.CLOUD,
        Categories.DEV_WEB,
        Categories.SECURITY,
        Categories.DEV_MOBILE);
  }
}
