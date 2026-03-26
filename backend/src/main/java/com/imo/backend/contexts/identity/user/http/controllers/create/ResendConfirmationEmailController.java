package com.imo.backend.contexts.identity.user.http.controllers.create;

import com.imo.backend.contexts.common.SimpleMessage;
import com.imo.backend.contexts.common.exceptions.ErrorResponseDto;
import com.imo.backend.contexts.identity.user.http.controllers.UserController;
import com.imo.backend.contexts.identity.user.http.dtos.ResendConfirmationEmailRequest;
import com.imo.backend.contexts.identity.user.usecases.ResendConfirmationEmailUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResendConfirmationEmailController extends UserController {
  private final ResendConfirmationEmailUseCase useCase;

  public ResendConfirmationEmailController(ResendConfirmationEmailUseCase useCase) {
    this.useCase = useCase;
  }

  @Operation(summary = "Reenviar email de confirmação")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Solicitação processada com sucesso", content = @Content(schema = @Schema(implementation = SimpleMessage.class))),
      @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
  })
  @PostMapping("/confirm/resend")
  public ResponseEntity<SimpleMessage> handle(
      @Valid
      @RequestBody
      ResendConfirmationEmailRequest request
  ) {
    this.useCase.execute(request.email());
    return ResponseEntity
        .ok()
        .body(new SimpleMessage(
            "Se o email estiver cadastrado, enviaremos um novo link de confirmação"));
  }
}
