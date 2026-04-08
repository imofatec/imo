package com.imo.backend.contexts.identity.recovery.http;

import com.imo.backend.contexts.common.SimpleMessage;
import com.imo.backend.contexts.identity.recovery.http.dtos.SendRecoveryCodeRequest;
import com.imo.backend.contexts.identity.recovery.usecases.SendForgetPasswordCodeUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SendForgetPasswordCodeController extends RecoveryController {
  private final SendForgetPasswordCodeUseCase sendForgetPasswordCodeUseCase;

  public SendForgetPasswordCodeController(
      SendForgetPasswordCodeUseCase sendForgetPasswordCodeUseCase) {
    this.sendForgetPasswordCodeUseCase = sendForgetPasswordCodeUseCase;
  }

  @Operation(summary = "Send code to user's email where he can change his password")
  @ApiResponses(
      value = {
        @ApiResponse(
            responseCode = "200",
            description = "Código enviado com sucesso",
            content = @Content(schema = @Schema(implementation = SimpleMessage.class))),
        @ApiResponse(
            responseCode = "400",
            description = "Email inválido ou dados incorretos",
            content =
                @Content(
                    schema =
                        @Schema(
                            implementation =
                                com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
      })
  @PostMapping("/password/send-code")
  public ResponseEntity<SimpleMessage> handle(@RequestBody SendRecoveryCodeRequest request) {
    String response = this.sendForgetPasswordCodeUseCase.execute(request.email());
    return ResponseEntity.ok(new SimpleMessage(response));
  }
}
