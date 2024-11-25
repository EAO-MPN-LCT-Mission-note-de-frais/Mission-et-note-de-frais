package com.diginamic.mission_note_de_frais.model.repository;

import com.diginamic.mission_note_de_frais.model.entity.ExpenseType;
import org.springframework.data.repository.CrudRepository;

/**
 * Repository pour la gestion des entités `ExpenseType`.
 *
 *  @author Marjory PRIN
 */
public interface ExpenseTypeRepository extends CrudRepository<ExpenseType, Long> {

    /**
     * Recherche une nature de frais par son nom.
     *
     * @param name Le nom de la nature de frais.
     * @return L'entité `ExpenseType` correspondant au nom de la nature de frais donnée.
     */
    public ExpenseType findByName(String name);
}
