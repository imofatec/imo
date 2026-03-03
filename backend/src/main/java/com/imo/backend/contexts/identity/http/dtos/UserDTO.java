package com.imo.backend.contexts.identity.http.dtos;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.value_objects.AcademicDegree;
import com.imo.backend.contexts.identity.value_objects.AvailableTimePerDay;
import com.imo.backend.contexts.identity.value_objects.ExperienceLevel;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;

import java.time.LocalDate;
import java.util.List;

public record UserDTO(
    String id,
    String name,
    String email,
    Boolean isConfirmed,
    String profilePicturePath,
    LocalDate birthDate,
    AvailableTimePerDay availableTimePerDay,
    AcademicDegree academicDegree,
    ExperienceLevel experienceLevel,
    List<Categories> categoriesOfInterest
) {
  public static UserDTO fromUser(User user) {
    return (user == null) ? null : new UserDTO(
        user.getId(),
        user.getName(),
        user.getEmail(),
        user.getIsConfirmed(),
        user.getProfilePicturePath(),
        user.getBirthDate(),
        user.getAvailableTimePerDay(),
        user.getAcademicDegree(),
        user.getExperienceLevel(),
        user.getCategoriesOfInterest()
    );
  }
}
