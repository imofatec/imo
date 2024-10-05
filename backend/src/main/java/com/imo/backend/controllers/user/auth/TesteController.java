package com.imo.backend.controllers.user.auth;

import com.imo.backend.controllers.user.UserController;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TesteController extends UserController {

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/private")
    public ResponseEntity<?> handle() {
        return ResponseEntity.ok().build();
    }
}
