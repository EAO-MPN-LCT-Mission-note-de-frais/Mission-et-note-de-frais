package com.diginamic.mission_note_de_frais.filter;

import com.diginamic.mission_note_de_frais.model.Account;
import com.diginamic.mission_note_de_frais.service.AccountService;
import com.diginamic.mission_note_de_frais.util.JsonWebToken;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * A filter that intercepts incoming requests and authenticates users using JWT tokens.
 * If the request does not contain an authentication token,
 * the filter does nothing and allows the request to proceed to the next filter in the chain.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  // The additional space at the end is required. Do not delete it.
  public static final String BEARER_AUTH_SCHEME = "bearer ";

  private final JsonWebToken jwts;
  private final AccountService accountService;

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain chain)
      throws ServletException, IOException {
    // Extract the Authorization header from the request
    String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    if (isNotAuthenticated() && hasValidAuthenticationToken(authorizationHeader)) {
      // Get the subject from the JWT token and use it to retrieve the user account
      String jwt = authorizationHeader.substring(7);
      String email = jwts.extract(jwt, Claims::getSubject);

      var account = accountService.getAccount(email);
      if (account != null) {
        // Validate the JWT token and authenticate the user
        if (validate(jwt, account)) {
          UsernamePasswordAuthenticationToken authenticationToken =
              new UsernamePasswordAuthenticationToken(account, null, account.getAuthorities());
          authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
      }
    }

    // Proceed to the next filter in the chain
    chain.doFilter(request, response);
  }

  private boolean validate(String jwt, Account account) {
    var subject = jwts.extract(jwt, Claims::getSubject);
    return !jwts.hasExpired(jwt) && account.getUsername().equals(subject);
  }

  private static boolean hasValidAuthenticationToken(String authorizationHeader) {
    return authorizationHeader != null && authorizationHeader.toLowerCase().startsWith(BEARER_AUTH_SCHEME);
  }

  private boolean isNotAuthenticated() {
    var authentication = SecurityContextHolder.getContext().getAuthentication();
    return authentication == null || !authentication.isAuthenticated();
  }
}