package com.imo.backend.contexts.identity.user.http.dtos;

import com.imo.backend.contexts.catalog.course.Categories;
import com.imo.backend.contexts.identity.user.AcademicDegree;
import com.imo.backend.contexts.identity.user.AvailableTimePerDay;
import com.imo.backend.contexts.identity.user.ExperienceLevel;
import com.imo.backend.contexts.identity.user.User;
import java.time.LocalDate;
import java.util.List;

public record CurrentUserProfileDTO(
    String id,
    String name,
    String email,
    String bio,
    Boolean isConfirmed,
    String profilePicturePath,
    LocalDate birthDate,
    AvailableTimePerDay availableTimePerDay,
    AcademicDegree academicDegree,
    ExperienceLevel experienceLevel,
    List<Categories> categoriesOfInterest) {
  public static CurrentUserProfileDTO fromUser(User user) {
    return (user == null)
        ? null
        : new CurrentUserProfileDTO(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getBio(),
            user.getIsConfirmed(),
            user.getProfilePicturePath(),
            user.getBirthDate(),
            user.getAvailableTimePerDay(),
            user.getAcademicDegree(),
            user.getExperienceLevel(),
            user.getCategoriesOfInterest());
  }
}
