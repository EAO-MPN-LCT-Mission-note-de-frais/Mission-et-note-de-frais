package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;

import java.util.List;

/**
 * Interface représentant le service de gestion des note de frais (`ExpenseReport`).
 *
 * TODO : Ajouter l'extract par collaborateur et par état +  l'ajout, la modif (état) et la suppression
 *
 * @author Marjory PRIN
 */
public interface ExpenseReportService {

    /**
     * Récupère toutes les notes de frais.
     *
     * @return Une liste d'entités `ExpenseReport`.
     */
    List<ExpenseReport> extractAllExpenseReport();

    /**
     * Récupère une note de frais par son identifiant unique.
     *
     * @param id L'identifiant unique de la note de frais à récupérer.
     * @return L'entité `ExpenseReport` correspondant à l'identifiant.
     */
    ExpenseReport getExpenseReportById(Long id);

}
