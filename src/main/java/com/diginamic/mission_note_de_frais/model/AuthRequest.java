package com.diginamic.mission_note_de_frais.model;

import lombok.Data;

/**
 * Represents a request to authenticate a user.
 */
@Data
public class AuthRequest {
    /**
     * The email of the user.
     */
    private String email;
    /**
     * The password of the user.
     */
    private String password;
}
