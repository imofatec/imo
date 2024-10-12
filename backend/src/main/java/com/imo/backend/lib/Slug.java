package com.imo.backend.lib;

import java.text.Normalizer;

public class Slug {

    public static String create(String name) {

        String slug = Normalizer.normalize(name.trim().toLowerCase(), Normalizer.Form.NFD)
                .replaceAll("ç", "c")
                .replaceAll("[^\\p{Alnum}\\s'-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("'", "")
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                .replaceAll("^-|-$", "");

        return slug;
    }

}
