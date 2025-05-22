package com.imo.backend.config.security;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Value("${jwt.public.key}")
  private RSAPublicKey publicKey;

  @Value("${jwt.private.key}")
  private RSAPrivateKey privateKey;

  @Value("${client.url}")
  private String clientURL;


  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
        .cors(Customizer.withDefaults())
        .csrf(AbstractHttpConfigurer::disable)
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(HttpMethod.PUT, "/api/user/profile-picture").authenticated()
            .requestMatchers(HttpMethod.PUT, "/api/user").authenticated()
            .requestMatchers(HttpMethod.PUT, "/api/user/progress/{courseId}").authenticated()
            .requestMatchers(HttpMethod.PUT, "/api/user/confirm").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/user/profile").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/user/private").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/user/course/certificate/{courseId}").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/user/courses/progress").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/user/course/progress/{courseId}").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/user/course/overviews").authenticated()
            .requestMatchers(HttpMethod.GET, "/api/user/contributions").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/courses").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/courses/many/courses").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/courses/comments/lessons/{lessonId}").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/courses/create").authenticated()
            .requestMatchers(HttpMethod.POST, "/api/courses/create-many").authenticated()
            .requestMatchers(HttpMethod.PUT, "/api/courses/{courseId}").authenticated()
            .anyRequest().permitAll()
        )
        .sessionManagement(
            session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .oauth2ResourceServer(conf -> conf
            .jwt(Customizer.withDefaults())
            .authenticationEntryPoint(new CustomAuthenticationEntryPoint())
        )
        .build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(this.clientURL));
    configuration.setAllowedMethods(
        Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowCredentials(true);
    configuration.setAllowedHeaders(
        List.of("Authorization", "Cache-Control", "Content-Type"));
    configuration.setExposedHeaders(List.of("Content-Disposition"));
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public JwtEncoder jwtEncoder() {
    JWK jwk = new RSAKey.Builder(this.publicKey).privateKey(privateKey).build();
    var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
    return new NimbusJwtEncoder(jwks);
  }

  @Bean
  public JwtDecoder jwtDecoder() {
    return NimbusJwtDecoder.withPublicKey(publicKey).build();
  }
}
