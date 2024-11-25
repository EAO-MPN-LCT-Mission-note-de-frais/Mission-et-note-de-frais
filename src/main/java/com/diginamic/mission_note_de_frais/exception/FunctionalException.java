package com.diginamic.mission_note_de_frais.exception;

/**
 * Exception personnalisée utilisée pour représenter des erreurs fonctionnelles dans l'application.
 * <p>
 * Cette exception est destinée à signaler des situations où une règle métier n'est pas respectée ou une
 * condition spécifique empêche le bon fonctionnement logique d'un processus.
 * </p>
 *
 * @author Marjory PRIN
 */
public class FunctionalException extends Exception {

    /**
     * Identifiant unique pour la sérialisation.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Constructeur pour créer une instance de `FunctionalException` avec un message spécifique.
     *
     * @param message Le message décrivant l'erreur fonctionnelle.
     */
    public FunctionalException(String message) {
        super(message);
    }
}
