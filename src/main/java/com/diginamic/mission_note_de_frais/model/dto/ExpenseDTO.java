package com.diginamic.mission_note_de_frais.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 *  DTO représentant une ligne de frais destinée à l'affichage
 *
 *  @author Marjory PRIN
 */
@Getter
@Setter
public class ExpenseDTO {

    /**
     * Date de la ligne de frais
     */
    private LocalDate date;

    /**
     * Description de la ligne de frais
     */
    private String description;

    /**
     * Nature du frais
     */
    private String expenseType;

    /**
     * Montant de la ligne de frais
     */
    private Double amount;

    /**
     * Pourcentage de TVA de la ligne de frais
     */
    private Double tax;
}
