package com.diginamic.mission_note_de_frais.exception;

import org.springframework.http.HttpStatus;

/**
 * Exception class representing domain input validation errors.
 */
public class BadRequestException extends DomainException {

    public BadRequestException(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }

    public BadRequestException(String message, Throwable cause) {
        super(HttpStatus.BAD_REQUEST, message, cause);
    }
}
