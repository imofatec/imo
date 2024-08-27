package com.imo.backend.config.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imo.backend.models.user.dtos.LoginResponse;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;

    private final ObjectMapper objectMapper;

    public TokenService(
            JwtEncoder jwtEncoder,
            ObjectMapper objectMapper
    ) {
        this.jwtEncoder = jwtEncoder;
        this.objectMapper = objectMapper;
    }

    public LoginResponse generateToken(String username, String id) {
        String sub;
        try {
            sub = objectMapper.writeValueAsString(new SubTokenDto(id, username));
        } catch (Exception e) {
            throw new RuntimeException("Erro serializando o token");
        }

        var now = Instant.now();
        var expiresIn = 300L;

        var claims = JwtClaimsSet.builder()
                .issuer("IMO")
                .issuedAt(now)
                .subject(sub)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        var token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(token, expiresIn);
    }
}
