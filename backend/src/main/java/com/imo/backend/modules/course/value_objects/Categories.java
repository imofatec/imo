package com.imo.backend.modules.course.value_objects;

import com.imo.backend.exceptions.custom.BadRequestException;
import lombok.Getter;

import java.util.Arrays;

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

  public static Categories safeParseFromString(String value) {
    try {
      return Categories.valueOf(value.toUpperCase());
    } catch (IllegalArgumentException e) {
      throw new BadRequestException(String.format(
          "Categoria inválida: %s\nCategorias válidas: %s",
          value,
          Arrays.stream(Categories.values()).toList()
      ));
    }
  }
}
