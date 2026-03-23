package com.imo.backend.lib.token;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.imo.backend.contexts.identity.http.dtos.auth.LoginResponseDTO;
import com.imo.backend.contexts.identity.lib.TokenManager;

import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class JWTokenManager implements TokenManager {

  private final JwtEncoder jwtEncoder;

  private final JwtDecoder jwtDecoder;

  private final ObjectMapper objectMapper;

  public JWTokenManager(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder, ObjectMapper objectMapper) {
    this.jwtEncoder = jwtEncoder;
    this.jwtDecoder = jwtDecoder;
    this.objectMapper = objectMapper;
  }

  public LoginResponseDTO generateToken(String userId) {
    var now = Instant.now();
    var expiresIn = 18000L; // 5h

    var claims = JwtClaimsSet
        .builder()
        .issuer("IMO")
        .issuedAt(now)
        .subject(userId)
        .expiresAt(now.plusSeconds(expiresIn))
        .build();

    String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    return new LoginResponseDTO(token, expiresIn);
  }

  public String getUserId(String token) {
    Jwt payload = jwtDecoder.decode(token.substring(7));

    return payload.getSubject();
  }
}
