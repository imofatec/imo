package com.imo.backend.api.controller;

import com.imo.backend.models.dto.CreateUserDto;
import com.imo.backend.models.user.User;
import com.imo.backend.models.user.UserRepository;
import com.imo.backend.models.user.service.CreateUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class UserController {


    private final CreateUserService createUserService;




    public UserController(CreateUserService createUserService) {
        this.createUserService = createUserService;

    }

    @PostMapping("/create")
    public ResponseEntity<String> create(@Valid @RequestBody CreateUserDto createUserDto) {
        createUserService.createUser(createUserDto);
        return new ResponseEntity<>("Cadastrado com sucesso!", HttpStatus.CREATED);
    }


}
