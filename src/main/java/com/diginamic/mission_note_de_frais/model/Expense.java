package com.diginamic.mission_note_de_frais.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.Date;

/**
 * Modèle représentant une ligne de frais par sa date, sa nature, sa description, son montant,
 * son pourcentage de TVA et la note de frais à laquelle elle est rattachée.
 *
 * @author Marjory PRIN
 */
@Entity
@Table(name = "EXPENSE")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class Expense {

    /**
     * Identifiant unique de la ligne de frais
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Date de la ligne de frais
     */
    @NonNull
    @Column(nullable = false)
    private Date date;

    /**
     * Nature de la ligne frais
     */
    @NonNull
    @ManyToOne
    private ExpenseType type;

    /**
     * Description de la ligne de frais
     */
    private String description;

    /**
     * Montant de la ligne de frais
     */
    @NonNull
    @Column(nullable = false)
    private Float amount;

    /**
     * Pourcentage de TVA de la ligne de frais
     */
    @NonNull
    @Column(nullable = false)
    private Float tax;

    /**
     * Note de frais associée
     */
    @NonNull
    @ManyToOne
    private ExpenseReport expenseReport;
}
