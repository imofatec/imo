package com.imo.backend.models.strategy.read;

import java.util.List;

// E - Elements
public interface GetManyToListService<E>  {
    List<E> execute();
}
