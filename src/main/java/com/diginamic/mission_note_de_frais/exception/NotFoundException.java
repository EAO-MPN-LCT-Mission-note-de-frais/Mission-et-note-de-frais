package com.diginamic.mission_note_de_frais.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception class representing domain entity not found errors.
 */
public class NotFoundException extends DomainException {

    public NotFoundException(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }

    public NotFoundException(String message, Throwable cause) {
        super(HttpStatus.NOT_FOUND, message, cause);
    }
}
