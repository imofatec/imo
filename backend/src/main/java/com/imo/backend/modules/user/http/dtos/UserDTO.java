package com.imo.backend.modules.user.http.dtos;

import com.imo.backend.modules.course.value_objects.Categories;
import com.imo.backend.modules.user.User;
import com.imo.backend.modules.user.value_objects.AcademicDegree;
import com.imo.backend.modules.user.value_objects.AvailableTimePerDay;
import com.imo.backend.modules.user.value_objects.ExperienceLevel;

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
