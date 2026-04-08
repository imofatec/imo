package com.imo.backend.contexts.identity.user.http.controllers.update;

import com.imo.backend.contexts.identity.user.http.controllers.UserController;
import com.imo.backend.contexts.identity.user.http.dtos.UserDTO;
import com.imo.backend.contexts.identity.user.usecases.UploadUserProfilePictureUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadUserProfilePictureController extends UserController {

  private final UploadUserProfilePictureUseCase useCase;

  public UploadUserProfilePictureController(UploadUserProfilePictureUseCase useCase) {
    this.useCase = useCase;
  }

  @Operation(
      summary = "Upload a profile picture",
      description = "Allow users to upload a new profile picture")
  @SecurityRequirement(name = "Authorization")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Foto de perfil atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = UserDTO.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Arquivo inválido",
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
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto
                                    .class))),
        @ApiResponse(
            responseCode = "404",
            description = "Usuário não encontrado",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @PutMapping(value = "/profile-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<UserDTO> handle(@RequestParam("file") MultipartFile file) {
    String userId = SecurityContextHolder.getContext().getAuthentication().getName();

    var updatedUser = UserDTO.fromUser(this.useCase.execute(userId, file));

    return ResponseEntity.ok(updatedUser);
  }
}
