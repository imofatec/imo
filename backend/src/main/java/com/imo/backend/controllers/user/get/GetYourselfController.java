package com.imo.backend.controllers.user.get;

import com.imo.backend.controllers.user.UserController;
import com.imo.backend.models.user.dtos.NoPasswordUser;
import com.imo.backend.models.user.service.GetYourselfService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GetYourselfController extends UserController {

    private final GetYourselfService getYourselfService;

    public GetYourselfController(GetYourselfService getYourselfService) {
        this.getYourselfService = getYourselfService;
    }

    @Operation(summary = "Get your data")
    @SecurityRequirement(name = "Authorization")
    @GetMapping("/profile")
    public NoPasswordUser handle(HttpServletRequest request) {
        return getYourselfService.execute(request.getHeader("Authorization"));
    }
}
