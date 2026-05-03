package com.imo.backend.contexts.common.http.dtos;

public record PaginationDTO(
    int currentPage,
    int pageSize,
    long totalItems,
    int totalPages,
    int remainingPages,
    boolean hasNext,
    boolean hasPrevious) {
  public static PaginationDTO from(int currentPage, int pageSize, long totalItems) {
    int totalPages = totalItems == 0 ? 0 : (int) Math.ceil((double) totalItems / pageSize);
    int remainingPages = Math.max(totalPages - currentPage - 1, 0);

    return new PaginationDTO(
        currentPage,
        pageSize,
        totalItems,
        totalPages,
        remainingPages,
        currentPage + 1 < totalPages,
        currentPage > 0);
  }
}
