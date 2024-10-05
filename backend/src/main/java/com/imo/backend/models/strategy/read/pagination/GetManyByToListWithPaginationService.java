package com.imo.backend.models.strategy.read.pagination;

import java.util.List;

public interface GetManyByToListWithPaginationService<E> {
    List<E> execute(String searchParam, Integer page, Integer size);
}
