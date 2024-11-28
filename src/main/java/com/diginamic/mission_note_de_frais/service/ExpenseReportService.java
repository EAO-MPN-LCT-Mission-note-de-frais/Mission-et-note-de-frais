package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.exception.FunctionalException;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;

/**
 * Interface représentant le service de gestion des notes de frais (`ExpenseReport`).
 *
 * @author Marjory PRIN
 */
public interface ExpenseReportService {

    /**
     * Récupère une note de frais par son identifiant unique.
     *
     * @param id L'identifiant unique de la note de frais à récupérer.
     * @return L'entité `ExpenseReport` correspondant à l'identifiant.
     */
    ExpenseReport getExpenseReportById(Long id);

    /**
     * Créer une nouvelle note de frais dans le système.
     *
     * @param expenseReport L'entité `Expense` à insérer.
     * @return `true` si l'insertion a réussi, `false` sinon.
     * @throws FunctionalException si une erreur métier survient lors de l'insertion de la ligne de frais.
     */
    boolean addExpenseReport(ExpenseReport expenseReport) throws FunctionalException;

    /**
     * Met à jour une note de frais existante.
     *
     * @param expenseReport L'entité `Expense` contenant les nouvelles informations à mettre à jour.
     * @return `true` si la mise à jour a réussi, `false` sinon.
     * @throws FunctionalException si une erreur métier survient lors de la mise à jour de la note de frais.
     */
    boolean updateExpenseReport(ExpenseReport expenseReport) throws FunctionalException;

    /**
     * Supprime une note de frais en fonction de son identifiant unique.
     *
     * @param id L'identifiant unique de la ligne de frais à supprimer.
     * @return `true` si la suppression a réussi, `false` sinon.
     */
    boolean deleteExpenseReport(Long id) throws FunctionalException;
}
