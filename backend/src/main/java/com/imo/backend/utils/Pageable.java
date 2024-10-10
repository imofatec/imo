package com.imo.backend.utils;

import com.imo.backend.exceptions.custom.BadRequestException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

public class Pageable {
    public static org.springframework.data.domain.Pageable fromPageSize(Integer page, Integer size) {
        if (page < 0 || size < 0) {
            throw new BadRequestException("O número ou tamanho da página precisam ser positivos");
        }

        if (size == 0) {
            throw new BadRequestException("O tamanho da página precisa ser maior do que zero");
        }

        return PageRequest.of(page, size, Sort.by("createdAt").descending());
    }
}
