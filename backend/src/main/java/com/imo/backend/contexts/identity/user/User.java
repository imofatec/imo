package com.imo.backend.contexts.identity.user;

import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import com.imo.backend.contexts.identity.user.value_objects.AcademicDegree;
import com.imo.backend.contexts.identity.user.value_objects.AvailableTimePerDay;
import com.imo.backend.contexts.identity.user.value_objects.ExperienceLevel;
import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;

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

  public User() {}

  public User(String name, String email, String password) {
    this.setName(name);
    this.setEmail(email);
    this.password = password;
    this.isConfirmed = false;
  }

  public User(String name, String email, String password, Boolean isConfirmed) {
    this.setName(name);
    this.setEmail(email);
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
      List<Categories> categoriesOfInterest) {
    this.setName(name);
    this.setEmail(email);
    this.password = password;
    this.isConfirmed = isConfirmed;
    this.setBirthDate(birthDate);
    this.availableTimePerDay = availableTimePerDay;
    this.academicDegree = academicDegree;
    this.experienceLevel = experienceLevel;
    this.setCategoriesOfInterest(categoriesOfInterest);
  }

  private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";

  public void setEmail(String email) {
    if (email == null || !Pattern.matches(EMAIL_REGEX, email)) {
      throw new BadRequestException("Formato de e-mail inválido");
    }
    this.email = email;
  }

  public void setName(String name) {
    if (name.length() > 20) {
      throw new BadRequestException("O nome deve ter no máximo 20 caracteres");
    }
    this.name = name;
  }

  public void setCategoriesOfInterest(List<Categories> categoriesOfInterest) {
    if (categoriesOfInterest.size() > 2) {
      throw new BadRequestException("São permitido no máximo 2 categorias de interesse");
    }
    this.categoriesOfInterest = categoriesOfInterest;
  }

  public void setBirthDate(LocalDate birthDate) {
    if (birthDate != null && birthDate.isAfter(LocalDate.now())) {
      throw new BadRequestException("A data de nascimento não pode ser maior que hoje");
    }

    this.birthDate = birthDate;
  }

  public String assertUploadProfilePicture(String filename, long sizeInBytes, String contentType) {
    if (filename == null || filename.isEmpty()) {
      throw new BadRequestException("Adicione um arquivo");
    }

    long fileSizeInMb = sizeInBytes / (1024 * 1024);
    if (fileSizeInMb > 10) {
      throw new BadRequestException("O arquivo não pode ter mais do que 10MB");
    }

    if (contentType == null) {
      throw new BadRequestException("Arquivo corrompido");
    }

    String regex = "image/jpg|image/jpeg|image/png|image/webp";
    Pattern pattern = Pattern.compile(regex);

    if (!pattern.matcher(contentType).matches()) {
      throw new BadRequestException(
          String.format(
              "O formato %s não é valido, só é válido imagens png, jpeg, jpg e webp", contentType));
    }

    return filename;
  }

  public void assertCanContributeCourse() {
    if (!Boolean.TRUE.equals(this.isConfirmed)) {
      throw new ForbiddenException(
          "Somente usuários com conta confirmada podem contribuir com cursos");
    }
  }
}
