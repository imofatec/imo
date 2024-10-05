package com.imo.backend.models.strategy.read.pagination;

import java.util.List;

public interface GetManyToListWithPaginationService<E> {
    List<E> execute(Integer page, Integer size);
}
