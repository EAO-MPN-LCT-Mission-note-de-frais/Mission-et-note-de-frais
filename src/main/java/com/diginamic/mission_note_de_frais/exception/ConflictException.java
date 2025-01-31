package com.diginamic.mission_note_de_frais.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception class representing domain entity already exists errors.
 */
public class ConflictException extends DomainException {

    public ConflictException(String message) {
        super(HttpStatus.CONFLICT, message);
    }

    public ConflictException(String message, Throwable cause) {
        super(HttpStatus.CONFLICT, message, cause);
    }
}
