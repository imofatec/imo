package com.imo.backend.contexts.identity.actions.helpers;

import com.imo.backend.contexts.identity.User;
import com.imo.backend.contexts.identity.actions.inputs.UpdateUserByIdInput;

public class UserUpdater {
  public static void apply(User user, UpdateUserByIdInput dto) {
    if (dto.name() != null && !dto.name().isEmpty()) {
      user.setName(dto.name());
    }

    if (dto.email() != null && !dto.email().isEmpty()) {
      user.setEmail(dto.email());
    }

    if (dto.password() != null && !dto.password().isEmpty()) {
      user.setPassword(dto.password());
    }

    if (dto.profilePicturePath() != null && !dto.profilePicturePath().isEmpty()) {
      user.setProfilePicturePath(dto.profilePicturePath());
    }

    if (dto.birthDate() != null) {
      user.setBirthDate(dto.birthDate());
    }

    if (dto.availableTimePerDay() != null) {
      user.setAvailableTimePerDay(dto.availableTimePerDay());
    }

    if (dto.academicDegree() != null) {
      user.setAcademicDegree(dto.academicDegree());
    }

    if (dto.experienceLevel() != null) {
      user.setExperienceLevel(dto.experienceLevel());
    }

    if (dto.categoriesOfInterest() != null) {
      user.setCategoriesOfInterest(dto.categoriesOfInterest());
    }
  }
}
