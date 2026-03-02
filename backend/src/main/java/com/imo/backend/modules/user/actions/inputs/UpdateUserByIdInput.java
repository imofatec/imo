package com.imo.backend.modules.user.actions.inputs;

import com.imo.backend.modules.course.value_objects.Categories;
import com.imo.backend.modules.user.value_objects.AcademicDegree;
import com.imo.backend.modules.user.value_objects.AvailableTimePerDay;
import com.imo.backend.modules.user.value_objects.ExperienceLevel;

import java.time.LocalDate;
import java.util.List;

public record UpdateUserByIdInput(
    String email,
    String name,
    String password,
    String profilePicturePath,
    LocalDate birthDate,
    AvailableTimePerDay availableTimePerDay,
    AcademicDegree academicDegree,
    ExperienceLevel experienceLevel,
    List<Categories> categoriesOfInterest
) {
}
