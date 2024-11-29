package com.diginamic.mission_note_de_frais.util;

import com.diginamic.mission_note_de_frais.EnvironmentVariables;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * A utility class for managing JSON Web Tokens (JWT).
 * This class provides methods for signing, verifying and extracting JWT tokens.
 */
@Component
@RequiredArgsConstructor
public class JsonWebToken {
  // Used to access environment variables from the application.properties file
  private final EnvironmentVariables environment;

  /**
   * Signs a JWT token with the given subject and claims.
   *
   * @param subject the subject of the token
   * @param claims  other claims to include in the token
   * @return the signed JWT token
   */
  public String sign(String subject, Map<String, ?> claims) {
    Instant issuedAt = Instant.now();
    Instant expiration = issuedAt.plus(environment.getJwtExpirySeconds(), ChronoUnit.SECONDS);

    return Jwts.builder()
        .claims(claims)
        .subject(subject)
        .issuedAt(Date.from(issuedAt))
        .expiration(Date.from(expiration))
        .signWith(getSecretKey())
        .compact();
  }

  /**
   * Extracts the claims from a JWT token.
   *
   * @param token the JWT token
   * @return the claims extracted from the token
   */
  Claims extract(String token) {
    return Jwts.parser()
        .verifyWith(getSecretKey())
        .build()
        .parseSignedClaims(token)
        .getPayload();
  }

  /**
   * Extracts a specific claim from a JWT token.
   *
   * @param token    the JWT token
   * @param resolver a function that extracts the desired claim from the token's claims
   * @param <T>      the type of the claim to extract
   * @return the extracted claim
   */
  public <T> T extract(String token, Function<Claims, T> resolver) {
    Claims claims = extract(token);
    return resolver.apply(claims);
  }

  /**
   * Verifies whether a JWT token has expired.
   *
   * @param token the JWT token
   * @return true if the token has expired, false otherwise
   */
  public boolean hasExpired(String token) {
    Date expiration = extract(token, Claims::getExpiration);
    return expiration.before(new Date());
  }

  private SecretKey getSecretKey() {
    return Keys.hmacShaKeyFor(Base64.getDecoder().decode(environment.getJwtSecret()));
  }
}