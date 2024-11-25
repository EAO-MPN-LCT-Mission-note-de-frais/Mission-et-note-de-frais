package com.diginamic.mission_note_de_frais.model.dto;

import lombok.Getter;
import lombok.Setter;

/**
 *  DTO représentant une nature de frais destinée à l'affichage
 *
 *  @author Marjory PRIN
 */
@Getter
@Setter
public class ExpenseTypeDTO {

    /**
     * Nom de la nature de frais
     */
    private String name;
}
