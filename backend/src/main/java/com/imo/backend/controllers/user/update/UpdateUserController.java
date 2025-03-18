package com.imo.backend.controllers.user.update;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.user.dtos.FieldsToUpdateUser;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.services.update.UpdateUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.stream.Stream;

@RestController
public class UpdateUserController extends UserController {

    private final UpdateUserService updateUserService;

    public UpdateUserController(UpdateUserService updateUserService) {
        this.updateUserService = updateUserService;
    }

    @Operation(summary = "Update the user's credentials, including name, password, and email.")
    @SecurityRequirement(name = "Authorization")
    @PutMapping("/update")
    public ResponseEntity<NoPasswordUser> handle(HttpServletRequest request,
                                                 @Valid @RequestBody FieldsToUpdateUser fieldsToUpdateUser) {

        return checkNoContent(fieldsToUpdateUser)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(
                updateUserService.execute(
                        null,
                        fieldsToUpdateUser,
                        request.getHeader("Authorization")
                )
        );
    }

    private static boolean checkNoContent(FieldsToUpdateUser fieldsToUpdateUser) {
        return Stream.of(fieldsToUpdateUser.getName(),
                        fieldsToUpdateUser.getPassword(),
                        fieldsToUpdateUser.getEmail())
                .allMatch(Objects::isNull);
    }
}
