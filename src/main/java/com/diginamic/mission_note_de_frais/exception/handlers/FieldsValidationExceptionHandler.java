package com.diginamic.mission_note_de_frais.exception.handlers;

import com.diginamic.mission_note_de_frais.exception.DomainExceptionResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


/**
 * Global exception handler for handling validation errors in request payloads.
 */
@ControllerAdvice
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FieldsValidationExceptionHandler {
    /**
     * Handles MethodArgumentNotValidException and returns a ResponseEntity with a standardized error response.
     *
     * @param exception The exception to be handled.
     * @return ResponseEntity with a standardized error response.
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handle(MethodArgumentNotValidException exception, HttpServletRequest request) {
        var response = new DomainExceptionResponse();
        List<Object> objects = new ArrayList<>();

        // Accumulate constraints for each field
        Map<String, Map<String, Object>> accumulator = new LinkedHashMap<>();

        BindingResult result = exception.getBindingResult();
        List<ObjectError> errors = result.getAllErrors();
        errors.forEach(error -> {
            FieldError fieldError = (FieldError) error;
            String field = fieldError.getField();

            // If the field is encountered for the first time, initialize a new map for its constraints
            accumulator.computeIfAbsent(field, key -> new LinkedHashMap<>());

            // Add the current constraint to the map for the field
            accumulator.get(field).put(field, error.getDefaultMessage());
        });

        // Create the final entry with all constraints for each field
        accumulator.forEach((field, constraints) -> {
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("property", field);
            entry.put("value", result.getFieldValue(field)); // You can use the original field value
            entry.put("constraints", constraints);
            objects.add(entry);
        });

        response.setPath(request.getRequestURI());
        response.setMessage(objects);
        response.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
