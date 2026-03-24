package com.imo.backend.http;

import com.imo.backend.contexts.common.SimpleMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Health Check", description = "Endpoint de verificação de saúde da aplicação")
@RestController
public class HealthCheckController {
  @Operation(summary = "Verificar saúde da aplicação", description = "Retorna OK se a aplicação estiver funcionando corretamente")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Aplicação funcionando corretamente",
          content = @Content(schema = @Schema(implementation = SimpleMessage.class)))
  })
  @GetMapping("/api/health-check")
  public ResponseEntity<SimpleMessage> handle() {
    return ResponseEntity.ok().body(new SimpleMessage("OK"));
  }
}
