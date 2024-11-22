package com.diginamic.mission_note_de_frais.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Modèle représentant une note de frais contenant des lignes de frais.
 *
 * @author Marjory PRIN
 */
@Entity
@Table(name = "EXPENSE_REPORT")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class ExpenseReport {

    /**
     * Identifiant unique de la note de frais
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Liste des lignes de frais associées
     */
    @NonNull
    @OneToMany(mappedBy = "expenseReport")
    private List<Expense> expenses = new ArrayList<>();

}
