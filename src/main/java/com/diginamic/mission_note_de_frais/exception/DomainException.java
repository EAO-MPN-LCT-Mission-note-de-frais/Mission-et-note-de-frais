package com.diginamic.mission_note_de_frais.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception class representing domain-related errors.
 * That is error produced by processing the business logic.
 */
@Getter
public class DomainException extends Exception {

    /**
     * The HTTP status associated with the exception.
     */
    private final HttpStatus status;


    /**
     * Constructs a new {@code DomainException} with the specified arguments.
     *
     * @param status  An HTTP status code.
     * @param message A descriptive error message.
     */
    public DomainException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    /**
     * Constructs a new {@code DomainException} with the specified arguments.
     *
     * @param status  An HTTP status code.
     * @param message A descriptive error message.
     * @param cause   The cause of the exception.
     */
    public DomainException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }
}

