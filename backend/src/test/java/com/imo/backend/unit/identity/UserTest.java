package com.imo.backend.unit.identity;

import static org.junit.jupiter.api.Assertions.*;

import com.imo.backend.contexts.catalog.course.value_objects.Categories;
import com.imo.backend.contexts.common.exceptions.custom.BadRequestException;
import com.imo.backend.contexts.common.exceptions.custom.ForbiddenException;
import com.imo.backend.contexts.identity.user.User;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {
  private final String baseName = "Kiss Shot";

  private final String baseEmail = "kissshot@email.com";

  @Test
  @DisplayName("exception (setName): nome maior que 30 caracteres")
  void shouldThrowBadRequestWhenNameExceeds30Chars() {
    assertThrows(
        BadRequestException.class,
        () -> new User("Kiss Shot Acerola Orion Heart Under Blade", baseEmail, "123456"));
  }

  @Test
  @DisplayName("happy path (setEmail): formato de email valido")
  void shouldAcceptValidEmail() {
    assertDoesNotThrow(() -> new User(baseName, baseEmail, "123456"));
  }

  @Test
  @DisplayName("exception (setEmail): formato de email invalido")
  void shouldThrowBadRequestWhenEmailIsInvalid() {
    assertThrows(BadRequestException.class, () -> new User(baseName, "email-invalido", "123456"));
  }

  @Test
  @DisplayName("exception (setCategoriesOfInterest): mais de 2 categorias")
  void shouldThrowBadRequestWhenMoreThan2Categories() {
    User user = new User(baseName, baseEmail, "123456");

    assertThrows(
        BadRequestException.class,
        () ->
            user.setCategoriesOfInterest(
                List.of(Categories.AI, Categories.DATA, Categories.CLOUD)));
  }

  @Test
  @DisplayName("happy path (assertUploadProfilePicture): arquivo PNG valido")
  void shouldReturnFilenameWhenValidPng() {
    User user = new User(baseName, baseEmail, "123456");

    String result = user.assertUploadProfilePicture("avatar.png", 2 * 1024 * 1024, "image/png");

    assertEquals("avatar.png", result);
  }

  @Test
  @DisplayName("exception (assertUploadProfilePicture): formato invalido")
  void shouldThrowBadRequestWhenInvalidFormat() {
    User user = new User(baseName, baseEmail, "123456");

    assertThrows(
        BadRequestException.class,
        () -> user.assertUploadProfilePicture("doc.pdf", 1024, "application/pdf"));
  }

  @Test
  @DisplayName("happy path (assertCanContributeCourse): usuário confirmado pode contribuir")
  void shouldAllowConfirmedUserToContributeCourse() {
    User user = new User(baseName, baseEmail, "123456", true);

    assertDoesNotThrow(user::assertCanContributeCourse);
  }

  @Test
  @DisplayName("exception (assertCanContributeCourse): usuário nao confirmado nao pode contribuir")
  void shouldThrowForbiddenWhenUserIsNotConfirmed() {
    User user = new User(baseName, baseEmail, "123456", false);

    assertThrows(ForbiddenException.class, user::assertCanContributeCourse);
  }
}
