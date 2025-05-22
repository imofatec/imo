package com.imo.backend.controllers.user.get;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.services.get.interfaces.GetUsersByIdsService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class GetUsersByIdsController extends UserController {
  private final GetUsersByIdsService getUsersByIdsService;

  public GetUsersByIdsController(GetUsersByIdsService getUsersByIdsService) {
    this.getUsersByIdsService = getUsersByIdsService;
  }

  @Operation(summary = "Get user by ids")
  @GetMapping("/ids")
  public ResponseEntity<List<NoPasswordUser>> handle(@RequestParam List<String> ids) {
    var users = this.getUsersByIdsService.execute(ids);
    return ResponseEntity.ok(users);
  }
}
