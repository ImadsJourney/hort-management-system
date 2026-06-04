package com.schoolms.school_management.auth;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import com.schoolms.school_management.user.User;

import lombok.RequiredArgsConstructor;

/**
 * JWTService
 *
 * Token wird hier zentral erstellt
 */
@Service
@RequiredArgsConstructor
public class JWTService {

  private final JwtEncoder jwtEncoder;

  public String generateToken(User user) {
    Instant now = Instant.now();

    JwtClaimsSet claims = JwtClaimsSet.builder()
        .issuer("hort-manager")
        .subject(user.getUsername())
        .claim("userId", user.getId())
        .issuedAt(now)
        .expiresAt(now.plus(2, ChronoUnit.HOURS))
        .build();

    JwsHeader header = JwsHeader.with(MacAlgorithm.HS256).build();

    return jwtEncoder
        .encode(JwtEncoderParameters.from(header, claims))
        .getTokenValue();
  }
}
