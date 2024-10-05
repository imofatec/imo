package com.imo.backend.models.strategy.get.many.pagination;

import java.util.List;

public interface GetManyByToListWithPaginationService<E> {
    List<E> execute(String searchParam, Integer page, Integer size);
}
