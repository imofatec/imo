package com.imo.backend.config.token;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.imo.backend.models.user.dtos.LoginResponse;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Map;

@Service
public class TokenService {

    private final JwtEncoder jwtEncoder;

    private final JwtDecoder jwtDecoder;

    private final ObjectMapper objectMapper;

    public TokenService(
            JwtEncoder jwtEncoder,
            JwtDecoder jwtDecoder,
            ObjectMapper objectMapper
    ) {
        this.jwtEncoder = jwtEncoder;
        this.jwtDecoder = jwtDecoder;
        this.objectMapper = objectMapper;
    }

    public LoginResponse generateToken(String name, String id) {
        String sub;
        try {
            sub = objectMapper.writeValueAsString(new SubTokenDto(id, name));
        } catch (Exception e) {
            throw new RuntimeException("Erro serializando o token");
        }

        var now = Instant.now();
        var expiresIn = 18000L; // 5h

        var claims = JwtClaimsSet.builder()
                .issuer("IMO")
                .issuedAt(now)
                .subject(sub)
                .expiresAt(now.plusSeconds(expiresIn))
                .build();

        String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

        return new LoginResponse(token, expiresIn);
    }

    public Map<String, String> getSub(String token) {
        Jwt payload = jwtDecoder.decode(token.substring(7));

        String subAsString = payload.getClaim("sub");

        Map<String, String> subAsMap;

        try {
            subAsMap = objectMapper.readValue(subAsString, new TypeReference<Map<String, String>>() {
            });
        } catch (Exception e) {
            throw new RuntimeException("Erro pegando sub do payload do token");
        }

        return subAsMap;
    }
}
