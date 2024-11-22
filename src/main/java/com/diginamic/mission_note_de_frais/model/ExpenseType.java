package com.diginamic.mission_note_de_frais.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Modèle représentant une nature de frais par son nom et la liste des ligne de frais associées.
 *
 * @author Marjory PRIN
 */
@Entity
@Table(name = "EXPENSE_TYPE")
@Data
@RequiredArgsConstructor
@NoArgsConstructor
public class ExpenseType {

    /**
     * Identifiant unique de la nature de frais
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nom de la nature de frais
     */
    @NonNull
    @Column(unique = true, nullable = false)
    private String name;

    /**
     * Liste des lignes de frais associées
     */
    @OneToMany(mappedBy = "type")
    private List<Expense> expenses = new ArrayList<>();


}
