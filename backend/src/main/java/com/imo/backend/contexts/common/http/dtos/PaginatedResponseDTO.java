package com.imo.backend.contexts.common.http.dtos;

import java.util.List;

public record PaginatedResponseDTO<T>(List<T> items, PaginationDTO pagination) {
  public static <T> PaginatedResponseDTO<T> from(
      List<T> items, int currentPage, int pageSize, long totalItems) {
    return new PaginatedResponseDTO<>(items, PaginationDTO.from(currentPage, pageSize, totalItems));
  }
}
