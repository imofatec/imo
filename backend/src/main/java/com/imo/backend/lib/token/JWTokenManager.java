package com.imo.backend.lib.token;

import com.imo.backend.contexts.common.exceptions.custom.UnauthorizedException;
import com.imo.backend.contexts.identity.user.http.dtos.auth.LoginResponseDTO;
import com.imo.backend.contexts.identity.user.lib.TokenManager;
import java.time.Instant;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Service;

@Service
public class JWTokenManager implements TokenManager {

  private final JwtEncoder jwtEncoder;

  private final JwtDecoder jwtDecoder;

  public JWTokenManager(JwtEncoder jwtEncoder, JwtDecoder jwtDecoder) {
    this.jwtEncoder = jwtEncoder;
    this.jwtDecoder = jwtDecoder;
  }

  public LoginResponseDTO generateToken(String userId) {
    var now = Instant.now();
    var expiresIn = 18000L; // 5h

    var claims =
        JwtClaimsSet.builder()
            .issuer("IMO")
            .issuedAt(now)
            .subject(userId)
            .expiresAt(now.plusSeconds(expiresIn))
            .build();

    String token = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();

    return new LoginResponseDTO(token, expiresIn);
  }

  public String getUserId(String token) {
    try {
      Jwt payload = jwtDecoder.decode(normalizeToken(token));
      return payload.getSubject();
    } catch (JwtException ex) {
      throw new UnauthorizedException("Token inválido ou expirado");
    }
  }

  private String normalizeToken(String token) {
    if (token.startsWith("Bearer ")) {
      return token.substring(7);
    }

    return token;
  }
}
