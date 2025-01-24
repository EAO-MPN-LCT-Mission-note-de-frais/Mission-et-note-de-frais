package com.diginamic.mission_note_de_frais.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Représente la structure de la réponse serveur retournée au client.
 * Utilisée pour uniformiser la réponse serveur dans les contrôleurs.
 *
 * @author Marjory PRIN
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponseDTO {
    /**
     * Le type de la réponse (ex: "Success", "Bad Request", "Not Found", "Internal Server Error").
     */
    private String type;

    /**
     * Le message détaillé pour l'utilisateur ou le développeur.
     */
    private String message;

    /**
     * Le code HTTP associé à la réponse (ex. 200, 400, 404, 500).
     */
    private int status;

    /**
     * La date et l'heure de la réponse
     */
    private LocalDateTime timestamp;

    /**
     * Un champ optionnel pour des données supplémentaires (par exemple, le payload de la réponse).
     */
    private Object data;
}
