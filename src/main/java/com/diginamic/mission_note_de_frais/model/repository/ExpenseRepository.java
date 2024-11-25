package com.diginamic.mission_note_de_frais.model.repository;

import com.diginamic.mission_note_de_frais.model.entity.Expense;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Repository pour la gestion des entités `Expense`.
 *
 *  @author Marjory PRIN
 */
public interface ExpenseRepository extends CrudRepository<Expense, Long> {

    /**
     * Recherche toutes les dépenses associées à une note de frais spécifique.
     *
     * @param expenseReport La note de frais (`ExpenseReport`) pour laquelle rechercher les dépenses.
     * @return Une liste d'entités `Expense` correspondant à la note de frais donnée.
     */
    List<Expense> findByExpenseReport(ExpenseReport expenseReport);
}
