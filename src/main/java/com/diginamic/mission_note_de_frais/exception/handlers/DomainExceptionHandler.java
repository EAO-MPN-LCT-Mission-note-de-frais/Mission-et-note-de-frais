
package com.diginamic.mission_note_de_frais.exception.handlers;

import com.diginamic.mission_note_de_frais.exception.DomainException;
import com.diginamic.mission_note_de_frais.exception.DomainExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for HTTP exceptions. Annotated with {@code @ControllerAdvice} to allow centralized handling of exceptions across multiple controllers.
 */
@ControllerAdvice
public class DomainExceptionHandler {

    /**
     * Handles instances of {@code HttpException} and generates a standardized response.
     *
     * @param exception The domain exception to be handled.
     * @return A {@link org.springframework.http.ResponseEntity} containing an {@link DomainException} with relevant information.
     */
    @ExceptionHandler(value = {DomainException.class})
    ResponseEntity<Object> handle(DomainException exception, HttpServletRequest request) {
        var response = new DomainExceptionResponse();
        response.setMessage(exception.getMessage());
        response.setPath(request.getRequestURI());
        response.setStatus(exception.getStatus().value());
        return new ResponseEntity<>(response, exception.getStatus());
    }
}

