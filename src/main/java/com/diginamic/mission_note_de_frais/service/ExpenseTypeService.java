package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.entity.ExpenseType;

import java.util.List;

/**
 * Interface représentant le service de gestion des natures de frais (`ExpenseType`).
 *
 * TODO : Ajouter la méthode d'ajout d'une nature de frais
 *
 * @author Marjory PRIN
 */
public interface ExpenseTypeService {

    /**
     * Récupère toutes les natures de frais.
     *
     * @return Une liste d'entités `ExpenseType`.
     */
    List<ExpenseType> extractAllExpenseTypes();

    /**
     * Récupère une nature de frais par son identifiant unique.
     *
     * @param id L'identifiant unique de la nature de frais à récupérer.
     * @return L'entité `ExpenseType` correspondant à l'identifiant.
     */
    ExpenseType extractExpenseTypeById(Long id);

    /**
     * Récupère une nature de frais par son nom.
     *
     * @param name Le nom de la nature de frais à récupérer.
     * @return L'entité `ExpenseType` correspondant au nom donné.
     */
    ExpenseType extractExpenseTypeByName(String name);
}
