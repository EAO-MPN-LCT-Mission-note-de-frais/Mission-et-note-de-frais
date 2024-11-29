package com.diginamic.mission_note_de_frais.controller;

import com.diginamic.mission_note_de_frais.model.AuthRequest;
import com.diginamic.mission_note_de_frais.model.AuthResponse;
import com.diginamic.mission_note_de_frais.service.AccountService;
import com.diginamic.mission_note_de_frais.util.JsonWebToken;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * Controller class for handling authentication-related requests.
 * This class provides an endpoint for user login.
 */
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final AccountService accountService;
    private final JsonWebToken jwts;

    /**
     * Handles user login requests.
     *
     * @param request the login request
     * @return a response entity containing the JWT token
     */
    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // Try to authenticate the user using the provided credentials
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );

            // If the authentication is successful, generate a JWT token and return it
            var account = accountService.getAccount(request.getEmail());
            var payload = Map.of("roles", account.getRoles());
            String jwt = jwts.sign(account.getEmail(), payload);

            return ResponseEntity.ok(new AuthResponse(jwt));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }
}