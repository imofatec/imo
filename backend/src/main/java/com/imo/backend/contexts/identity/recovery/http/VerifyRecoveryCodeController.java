package com.imo.backend.contexts.identity.recovery.http;

import com.imo.backend.contexts.common.SimpleMessage;
import com.imo.backend.contexts.identity.recovery.RecoveryCodePolicy;
import com.imo.backend.contexts.identity.recovery.http.dtos.VerifyRecoveryCodeRequest;
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
public class VerifyRecoveryCodeController extends RecoveryController {
  private final RecoveryCodePolicy policy;

  public VerifyRecoveryCodeController(RecoveryCodePolicy policy) {
    this.policy = policy;
  }

  @Operation(summary = "Verificar código de recuperação", description = "Verifica se o código de recuperação é válido para o email informado")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Código validado com sucesso",
          content = @Content(schema = @Schema(implementation = SimpleMessage.class))),
      @ApiResponse(responseCode = "400", description = "Código inválido ou expirado",
          content = @Content(schema = @Schema(implementation = com.imo.backend.contexts.common.exceptions.ErrorResponseDto.class)))
  })
  @PostMapping("/password/verify")
  public ResponseEntity<SimpleMessage> handle(
      @RequestBody
      VerifyRecoveryCodeRequest request
  ) {
    this.policy.assertCanRecovery(request.code(), request.email());

    return ResponseEntity.ok().body(new SimpleMessage("Código validado com sucesso"));
  }
}
