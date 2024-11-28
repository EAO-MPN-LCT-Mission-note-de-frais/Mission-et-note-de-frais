package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.exception.FunctionalException;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseType;

import java.util.List;

/**
 * Interface représentant le service de gestion des natures de frais (`ExpenseType`).
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

    /**
     * Insère une nouvelle nature de frais dans le système.
     *
     * @param expenseType L'entité `ExpenseType` à insérer.
     * @return `true` si l'insertion a réussi, `false` sinon.
     * @throws FunctionalException si une erreur métier survient lors de l'insertion de la nature de frais.
     */
    boolean insertExpenseType(ExpenseType expenseType) throws FunctionalException;

    /**
     * Met à jour une nature de frais existante.
     *
     * @param expenseType L'entité `ExpenseType` contenant les nouvelles informations à mettre à jour.
     * @return `true` si la mise à jour a réussi, `false` sinon.
     * @throws FunctionalException si une erreur métier survient lors de la mise à jour de la nature de frais.
     */
    boolean updateExpenseType(ExpenseType expenseType) throws FunctionalException;

    /**
     * Supprime une nature de frais en fonction de son identifiant unique.
     *
     * @param id L'identifiant unique de la nature de frais à supprimer.
     * @return `true` si la suppression a réussi, `false` sinon.
     */
    boolean deleteExpenseType(Long id) throws FunctionalException;

}
