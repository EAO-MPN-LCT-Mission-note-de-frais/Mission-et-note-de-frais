package com.diginamic.mission_note_de_frais.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

/**
 * Represents a response structure for HTTP exceptions.
 */
@Getter
@Setter
public class DomainExceptionResponse {

    /**
     * A descriptive message providing additional information about the exception.
     */
    private Object message;

    /**
     * The HTTP request url called when the error was produced
     */
    private String path;

    /**
     * The HTTP request status code.
     */
    private int status;

    /**
     * The timestamp when the exception response was created.
     */
    private Instant timestamp = Instant.now();

    @Override
    public String toString() {
        return "DomainExceptionResponse{" +
                "message=" + message +
                ", path='" + path + '\'' +
                ", status=" + status +
                ", timestamp=" + timestamp +
                '}';
    }
}
