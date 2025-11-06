package com.imo.backend.modules.user;

import com.imo.backend.exceptions.custom.BadRequestException;
import com.imo.backend.modules.base.Entity;
import com.imo.backend.modules.course.value_objects.Categories;
import com.imo.backend.modules.user.value_objects.AcademicDegree;
import com.imo.backend.modules.user.value_objects.AvailableTimePerDay;
import com.imo.backend.modules.user.value_objects.ExperienceLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.regex.Pattern;

@EqualsAndHashCode(callSuper = true)
@Document("users")
@Data
public class User extends Entity {

  private String name;

  private String email;

  private String password;

  private Boolean isConfirmed;

  private String profilePicturePath;

  private LocalDate birthDate;

  private AvailableTimePerDay availableTimePerDay;

  private AcademicDegree academicDegree;

  private ExperienceLevel experienceLevel;

  private List<Categories> categoriesOfInterest;

  public User() {
  }

  public User(String name, String email, String password, Boolean isConfirmed) {
    this.setName(name);
    this.email = email;
    this.password = password;
    this.isConfirmed = isConfirmed;
  }

  public User(
      String name,
      String email,
      String password,
      Boolean isConfirmed,
      LocalDate birthDate,
      AvailableTimePerDay availableTimePerDay,
      AcademicDegree academicDegree,
      ExperienceLevel experienceLevel,
      List<Categories> categoriesOfInterest
  ) {
    this.setName(name);
    this.email = email;
    this.password = password;
    this.isConfirmed = isConfirmed;
    this.birthDate = birthDate;
    this.availableTimePerDay = availableTimePerDay;
    this.academicDegree = academicDegree;
    this.experienceLevel = experienceLevel;
    this.setCategoriesOfInterest(categoriesOfInterest);
  }


  public void setName(String name) {
    if (name.length() < 30) {
      this.name = name;
      return;
    }

    var oldName = name;
    var splitName = oldName.split(" ");
    var formattedNamePt1 = splitName[0] + " ";
    var formattedNamePt2 = "";

    var potentialPrepositionInName = splitName[splitName.length - 2];
    if (potentialPrepositionInName.toLowerCase().matches("^(de|do|da)$")) {
      formattedNamePt2 = potentialPrepositionInName + " ";
    }

    var formattedNamePt3 = splitName[splitName.length - 1];

    this.name = formattedNamePt1.toUpperCase()
                + formattedNamePt2.toUpperCase()
                + formattedNamePt3.toUpperCase();
  }

  public void setCategoriesOfInterest(List<Categories> categoriesOfInterest) {
    if (categoriesOfInterest.size() > 2) {
      throw new BadRequestException("São permitido no máximo 2 categorias de interesse");
    }
    this.categoriesOfInterest = categoriesOfInterest;
  }

  public static LocalDate getBirthDateFromString(String birthDate) {
    String pattern = "^(\\d{4})\\/(0[1-9]|1[0-2])\\/(0[1-9]|[12]\\d|3[01])$";

    if (!Pattern.matches(pattern, birthDate)) {
      throw new BadRequestException("Preencha a data no formato de yyyy/MM/dd");
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    return LocalDate.parse(birthDate, formatter);
  }
}