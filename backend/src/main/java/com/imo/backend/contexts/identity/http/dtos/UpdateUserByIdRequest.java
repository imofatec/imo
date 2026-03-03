package com.imo.backend.contexts.identity.http.dtos;

import com.imo.backend.contexts.identity.value_objects.AcademicDegree;
import com.imo.backend.contexts.identity.value_objects.AvailableTimePerDay;
import com.imo.backend.contexts.identity.value_objects.ExperienceLevel;
import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateUserByIdRequest(
    @Email(message = "Insira um email válido")
    String email,

    @Size(min = 5, max = 20, message = "O seu nome precisa ter no mínimo 5 caracteres")
    String name,

    @Pattern(regexp = "^(?=.*[A-Z])(?=.*\\d).+$", message = "A senha precisa ter no mínimo uma letra maiúscula e 1 número")
    @Size(min = 8, message = "A senha precisa ter no mínimo 8 caracteres")
    @Size(max = 50, message = "A senha não pode ter mais do que 50 caracteres")
    String password,

    @Pattern(regexp = "^(\\d{4})\\/(0[1-9]|1[0-2])\\/(0[1-9]|[12]\\d|3[01])$", message = "Preencha a data no formato de yyyy/MM/DD")
    String birthDate,

    AvailableTimePerDay availableTimePerDay,

    AcademicDegree academicDegree,

    ExperienceLevel experienceLevel,

    List<Categories> categoriesOfInterest
) {
}
