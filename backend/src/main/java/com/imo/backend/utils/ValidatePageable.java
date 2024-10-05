package com.imo.backend.utils;

import com.imo.backend.exceptions.custom.BadRequestException;

public class ValidatePageable {
    public static void validate(Integer page, Integer size) {
        if (page < 0 || size < 0) {
            throw new BadRequestException("O número ou tamanho da página precisam ser positivos");
        }

        if (size == 0) {
            throw new BadRequestException("O tamanho da página precisa ser maior do que zero");
        }
    }
}
