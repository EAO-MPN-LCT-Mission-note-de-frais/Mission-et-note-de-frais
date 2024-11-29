package com.diginamic.mission_note_de_frais.model;

/**
 * Represents a response to an authentication request.
 *
 * @param token the JWT string
 */
public record AuthResponse(String token) { }
