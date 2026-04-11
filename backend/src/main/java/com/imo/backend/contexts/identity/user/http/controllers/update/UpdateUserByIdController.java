package com.imo.backend.contexts.identity.user.http.controllers.update;

import com.imo.backend.contexts.identity.user.commands.UpdateUserByIdCommand;
import com.imo.backend.contexts.identity.user.http.controllers.UserController;
import com.imo.backend.contexts.identity.user.http.dtos.UpdateUserByIdRequest;
import com.imo.backend.contexts.identity.user.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.user.usecases.UpdateUserByIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class UpdateUserByIdController extends UserController {
  private final UpdateUserByIdUseCase updateUserByIdUseCase;

  public UpdateUserByIdController(UpdateUserByIdUseCase updateUserByIdUseCase) {
    this.updateUserByIdUseCase = updateUserByIdUseCase;
  }

  @Operation(summary = "Update the user's credentials")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Usuário atualizado com sucesso",
            content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(responseCode = "204", description = "Nenhum campo para atualizar"),
        @ApiResponse(
            responseCode = "400",
            description = "Dados inválidos",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto
                                    .class))),
        @ApiResponse(
            responseCode = "401",
            description = "Não autenticado",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @PutMapping()
  public ResponseEntity<UserDTO> handle(
      @Valid @RequestBody UpdateUserByIdRequest fieldsToUpdateUser) {

    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    if (checkNoContent(fieldsToUpdateUser)) {
      return ResponseEntity.noContent().build();
    }

    LocalDate parsedBirthDate = null;
    if (fieldsToUpdateUser.birthDate() != null) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
      parsedBirthDate = LocalDate.parse(fieldsToUpdateUser.birthDate(), formatter);
    }

    UpdateUserByIdCommand cmd =
        new UpdateUserByIdCommand(
            fieldsToUpdateUser.email(),
            fieldsToUpdateUser.name(),
            fieldsToUpdateUser.password(),
            null,
            parsedBirthDate,
            fieldsToUpdateUser.availableTimePerDay(),
            fieldsToUpdateUser.academicDegree(),
            fieldsToUpdateUser.experienceLevel(),
            fieldsToUpdateUser.categoriesOfInterest());

    return ResponseEntity.ok(UserDTO.fromUser(this.updateUserByIdUseCase.execute(userId, cmd)));
  }

  private static boolean checkNoContent(UpdateUserByIdRequest fieldsToUpdateUser) {
    return Stream.of(fieldsToUpdateUser).allMatch(Objects::isNull);
  }
}
