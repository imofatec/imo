package com.imo.backend.controllers.user.get;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.lib.ValidateObjectId;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.services.get.interfaces.GetUserByIdService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetUserByIdController extends UserController {
  private final GetUserByIdService getUserByIdService;

  public GetUserByIdController(GetUserByIdService getUserByIdService) {
    this.getUserByIdService = getUserByIdService;
  }

  @Operation(summary = "Get user by id")
  @GetMapping("/{id}")
  public ResponseEntity<NoPasswordUser> handle(@PathVariable String id) {
    ValidateObjectId.execute(id);
    var user = this.getUserByIdService.execute(id);
    return ResponseEntity.ok(user);
  }
}
