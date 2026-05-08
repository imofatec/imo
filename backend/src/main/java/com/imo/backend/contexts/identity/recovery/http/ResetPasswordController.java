package com.imo.backend.contexts.identity.recovery.http;

import com.imo.backend.contexts.common.SimpleMessage;
import com.imo.backend.contexts.identity.recovery.http.dtos.ResetPasswordRequest;
import com.imo.backend.contexts.identity.recovery.usecases.RecoveryPasswordUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResetPasswordController extends RecoveryController {
  private final RecoveryPasswordUseCase useCase;

  public ResetPasswordController(RecoveryPasswordUseCase useCase) {
    this.useCase = useCase;
  }

  @Operation(
      summary = "Resetar senha",
      description = "Reseta a senha do usuário usando o código de recuperação")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Senha atualizada com sucesso",
            content = @Content(schema = @Schema(implementation = SimpleMessage.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Código inválido, expirado ou dados incorretos",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @PatchMapping("/password/reset")
  public ResponseEntity<SimpleMessage> handle(@RequestBody ResetPasswordRequest request) {

    String response = this.useCase.execute(request.email(), request.newPassword(), request.code());

    return ResponseEntity.ok().body(new SimpleMessage(response));
  }
}
