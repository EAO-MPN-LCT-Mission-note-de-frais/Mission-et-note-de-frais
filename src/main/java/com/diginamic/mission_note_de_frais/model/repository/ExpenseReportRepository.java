package com.diginamic.mission_note_de_frais.model.repository;

import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository pour la gestion des entités `ExpenseReport`.
 *
 *  @author Marjory PRIN
 */
public interface ExpenseReportRepository extends CrudRepository<ExpenseReport, Long> {

}
