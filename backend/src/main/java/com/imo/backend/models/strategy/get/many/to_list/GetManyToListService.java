package com.imo.backend.models.strategy.get.many.to_list;

import java.util.List;

// E - Elements
public interface GetManyToListService<E>  {
    List<E> execute();
}
