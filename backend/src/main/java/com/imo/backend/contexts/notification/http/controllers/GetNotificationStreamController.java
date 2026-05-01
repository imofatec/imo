package com.imo.backend.contexts.notification.http.controllers;

import com.imo.backend.contexts.common.exceptions.ErrorResponseDto;
import com.imo.backend.contexts.identity.user.lib.TokenManager;
import com.imo.backend.contexts.notification.lib.NotificationProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@RestController
public class GetNotificationStreamController extends NotificationController {
  private final TokenManager tokenManager;

  private final NotificationProvider notificationProvider;

  public GetNotificationStreamController(
      TokenManager tokenManager, NotificationProvider notificationProvider) {
    this.tokenManager = tokenManager;
    this.notificationProvider = notificationProvider;
  }

  @Operation(summary = "Open notification SSE stream")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Conexão SSE estabelecida"),
        @ApiResponse(
            responseCode = "401",
            description = "Token inválido ou expirado",
            content = @Content(schema = @Schema(implementation = ErrorResponseDto.class)))
      })
  @GetMapping(path = "/stream/{token:.+}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public ResponseEntity<SseEmitter> handle(@PathVariable String token) {
    String userId = this.tokenManager.getUserId(token);
    return ResponseEntity.ok(this.notificationProvider.connect(userId));
  }
}
