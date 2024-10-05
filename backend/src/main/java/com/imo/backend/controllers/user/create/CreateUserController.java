package com.imo.backend.controllers.user.create;


import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.user.dtos.RegisterUserRequest;
import com.imo.backend.models.user.dtos.RegisterUserResponse;
import com.imo.backend.models.user.service.CreateUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateUserController extends UserController {

    private final CreateUserService createUserService;

    public CreateUserController(CreateUserService createUserService) {
        this.createUserService = createUserService;
    }

    @Operation(summary = "Create an account")
    @PostMapping("/create")
    public ResponseEntity<RegisterUserResponse> handle(@Valid @RequestBody RegisterUserRequest registerUserRequest) {
        var newUser = createUserService.execute(registerUserRequest);
        return new ResponseEntity<>(newUser, HttpStatus.CREATED);
    }
}
