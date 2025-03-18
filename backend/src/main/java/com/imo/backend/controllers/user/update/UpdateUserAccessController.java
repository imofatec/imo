package com.imo.backend.controllers.user.update;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.services.update.interfaces.IUpdateAccessUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UpdateUserAccessController extends UserController { 
    private final IUpdateAccessUserService updateAccessUserService;

    public UpdateUserAccessController(IUpdateAccessUserService updateAccessUserService) {
        this.updateAccessUserService = updateAccessUserService;
    }

    @Operation(summary = "Confirm user registration")
    @SecurityRequirement(name = "Authorization")
    @PutMapping("/confirm")
    public ResponseEntity<NoPasswordUser> handle(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization");

        NoPasswordUser updatedUser = updateAccessUserService.execute(authToken);
        return ResponseEntity.ok(updatedUser);
    }
}