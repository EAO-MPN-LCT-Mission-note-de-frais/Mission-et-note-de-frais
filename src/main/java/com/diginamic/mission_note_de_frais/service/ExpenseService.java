package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.exception.FunctionalException;
import com.diginamic.mission_note_de_frais.model.entity.Expense;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;

import java.util.List;

/**
 * Interface représentant le service de gestion des lignes de frais (`Expense`).
 *
 * @author Marjory PRIN
 */
public interface ExpenseService {

    /**
     * Récupère une ligne de frais par son identifiant unique.
     *
     * @param id L'identifiant unique de la ligne de frais à récupérer.
     * @return L'entité `Expense` correspondant à l'identifiant.
     */
    Expense extractExpenseById(Long id);

    /**
     * Récupère toutes les lignes de frais associées à une note de frais donnée.
     *
     * @param expenseReport La note de frais à laquelle sont associées les lignes de frais.
     * @return Une liste d'entités `Expense` liées à la note de frais.
     */
    List<Expense> extractExpensesByExpenseReport(ExpenseReport expenseReport);

    /**
     * Insère une nouvelle ligne de frais dans le système.
     *
     * @param expense L'entité `Expense` à insérer.
     * @return `true` si l'insertion a réussi, `false` sinon.
     * @throws FunctionalException si une erreur métier survient lors de l'insertion de la ligne de frais.
     */
    boolean insertExpense(Expense expense) throws FunctionalException;

    /**
     * Met à jour une ligne de frais existante.
     *
     * @param expense L'entité `Expense` contenant les nouvelles informations à mettre à jour.
     * @return `true` si la mise à jour a réussi, `false` sinon.
     * @throws FunctionalException si une erreur métier survient lors de la mise à jour de la ligne de frais.
     */
    boolean updateExpense(Expense expense) throws FunctionalException;

    /**
     * Supprime une ligne de frais en fonction de son identifiant unique.
     *
     * @param id L'identifiant unique de la ligne de frais à supprimer.
     * @return `true` si la suppression a réussi, `false` sinon.
     */
    boolean deleteExpense(Long id);
}
