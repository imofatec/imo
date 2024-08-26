package com.imo.backend.api.controller;

import com.imo.backend.models.dto.CreateUserDto;
import com.imo.backend.models.dto.LoginRequest;
import com.imo.backend.models.dto.LoginResponse;
import com.imo.backend.models.user.service.AuthenticationService;
import com.imo.backend.models.user.service.CreateUserService;
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
            AuthenticationService authenticationService
    ) {
        this.createUserService = createUserService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody CreateUserDto createUserDto) {
        createUserService.createUser(createUserDto);
        return new ResponseEntity<>("Cadastrado com sucesso!", HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        var token = authenticationService.execute(loginRequest);
        return ResponseEntity.ok(token);
    }

    @GetMapping("/private")
    public ResponseEntity<?> privateRoute() {
        return ResponseEntity.ok().build();
    }
}
