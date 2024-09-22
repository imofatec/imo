package com.imo.backend.utils;

import java.text.Normalizer;

public class Slug {

    public static String create(String name) {

        String slug = Normalizer.normalize(name.trim().toLowerCase().replaceAll("[^\\p{Alnum}\\s'-]", ""), Normalizer.Form.NFD)
                .replaceAll("\\s+", "-")
                .replaceAll("'", "")
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("^-|-$", "");

        return slug;
    }

}
