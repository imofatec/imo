package com.imo.backend.lib.code;

import com.imo.backend.contexts.identity.recovery.lib.CodeGenerator;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class SecureRandomCodeGenerator implements CodeGenerator {
  private final SecureRandom secureRandom = new SecureRandom();

  @Override
  public String generate() {
    int code = this.secureRandom.nextInt(1_000_000);
    return String.format("%06d", code);
  }
}
