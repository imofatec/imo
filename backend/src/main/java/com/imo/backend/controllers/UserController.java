package com.imo.backend.controllers;

import com.imo.backend.models.user.dtos.RegisterUserRequest;
import com.imo.backend.models.user.dtos.LoginRequest;
import com.imo.backend.models.user.dtos.LoginResponse;
import com.imo.backend.models.user.dtos.RegisterUserResponse;
import com.imo.backend.models.user.service.AuthenticationService;
import com.imo.backend.models.user.service.CreateUserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class UserController {

    private final CreateUserService createUserService;

    private final AuthenticationService authenticationService;

    public UserController(
            CreateUserService createUserService,
            AuthenticationService authenticationService) {
        this.createUserService = createUserService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/create")
    public ResponseEntity<RegisterUserResponse> create(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        var newUser = createUserService.execute(registerUserRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        var token = authenticationService.execute(loginRequest);
        return ResponseEntity.ok(token);
    }

    @SecurityRequirement(name = "Authorization")
    @GetMapping("/private")
    public ResponseEntity<?> privateRoute() {
        return ResponseEntity.ok().build();
    }
}
