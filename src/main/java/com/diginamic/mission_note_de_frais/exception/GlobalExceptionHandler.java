package com.diginamic.mission_note_de_frais.exception;

import com.diginamic.mission_note_de_frais.model.dto.ApiResponseDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Gestionnaire global des exceptions pour le traitement des erreurs.
 *
 * Ce gestionnaire permet de centraliser la gestion des erreurs serveur et de retourner des réponses cohérentes au client.
 *
 * @author Marjory PRIN
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Gère les exceptions de type FunctionalException.
     *
     * @param ex L'exception à traiter.
     * @return Une réponse contenant un message d'erreur spécifique.
     */
    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<ApiResponseDTO> handleFunctionalException(FunctionalException ex) {
        ApiResponseDTO errorResponse = new ApiResponseDTO(
                "Bad Request",
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now(),
                null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gère les exceptions de type EntityNotFoundException.
     *
     * @param ex L'exception à traiter.
     * @return Une réponse contenant un message d'erreur spécifique.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ApiResponseDTO> handleEntityNotFoundException(EntityNotFoundException ex) {
        ApiResponseDTO errorResponse = new ApiResponseDTO(
                "Not Found",
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now(),
                null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    /**
     * Gère toutes les autres exceptions non gérées.
     *
     * @param ex L'exception générique à traiter.
     * @return Une réponse contenant un message d'erreur générique.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO> handleGeneralException(Exception ex) {
        ApiResponseDTO errorResponse = new ApiResponseDTO(
                "Internal Server Error",
                "Une erreur interne du serveur est survenue.",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now(),
                null
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
