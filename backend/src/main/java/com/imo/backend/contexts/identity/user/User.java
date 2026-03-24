package com.imo.backend.contexts.identity.user;

import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.common.Entity;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.identity.user.commands.UpdateUserByIdCommand;
import com.imo.backend.contexts.identity.user.value_objects.AcademicDegree;
import com.imo.backend.contexts.identity.user.value_objects.AvailableTimePerDay;
import com.imo.backend.contexts.identity.user.value_objects.ExperienceLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.multipart.MultipartFile;

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

  public String assertUploadProfilePicture(MultipartFile file) {
    if (file.isEmpty()) {
      throw new BadRequestException("Adicione um arquivo");
    }

    long fileSizeInMb = file.getSize() / (1024 * 1024);
    if (fileSizeInMb > 10) {
      throw new BadRequestException("O arquivo não pode ter mais do que 10MB");
    }

    if (file.getContentType() == null || file.getOriginalFilename() == null) {
      throw new BadRequestException("Arquivo corrompido");
    }

    String filename = file.getOriginalFilename();

    String regex = "image/jpg|image/jpeg|image/png";
    Pattern pattern = Pattern.compile(regex);

    if (!pattern.matcher(file.getContentType()).matches()) {
      throw new BadRequestException(String.format("O formato %s não é valido, só é válido imagens png, jpeg e jpg",
          file.getContentType()
      ));
    }

    return filename;
  }

  public static void applyUpdate(User user, UpdateUserByIdCommand cmd) {
    if (cmd.name() != null && !cmd.name().isEmpty()) {
      user.setName(cmd.name());
    }

    if (cmd.email() != null && !cmd.email().isEmpty()) {
      user.setEmail(cmd.email());
    }

    if (cmd.password() != null && !cmd.password().isEmpty()) {
      user.setPassword(cmd.password());
    }

    if (cmd.profilePicturePath() != null && !cmd.profilePicturePath().isEmpty()) {
      user.setProfilePicturePath(cmd.profilePicturePath());
    }

    if (cmd.birthDate() != null) {
      user.setBirthDate(cmd.birthDate());
    }

    if (cmd.availableTimePerDay() != null) {
      user.setAvailableTimePerDay(cmd.availableTimePerDay());
    }

    if (cmd.academicDegree() != null) {
      user.setAcademicDegree(cmd.academicDegree());
    }

    if (cmd.experienceLevel() != null) {
      user.setExperienceLevel(cmd.experienceLevel());
    }

    if (cmd.categoriesOfInterest() != null) {
      user.setCategoriesOfInterest(cmd.categoriesOfInterest());
    }
  }
}