package com.imo.backend.contexts.social.profile;

import com.imo.backend.contexts.catalog.course.Categories;
import java.util.List;

public record PublicUserDetails(
    String id,
    String name,
    String bio,
    String profilePicturePath,
    List<Categories> categoriesOfInterest) {}
