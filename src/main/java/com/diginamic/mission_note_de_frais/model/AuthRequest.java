package com.diginamic.mission_note_de_frais.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a request to authenticate a user.
 */
@Getter
@Setter
@ToString
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
