package com.imo.backend.controllers.user.auth;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.user.dtos.LoginRequest;
import com.imo.backend.models.user.dtos.LoginResponse;
import com.imo.backend.models.user.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationUserController extends UserController {

    private final AuthenticationService authenticationService;

    public AuthenticationUserController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> handle(@Valid @RequestBody LoginRequest loginRequest) {
        var token = authenticationService.execute(loginRequest);
        return ResponseEntity.ok(token);
    }

}
