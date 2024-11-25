package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.entity.ExpenseType;
import com.diginamic.mission_note_de_frais.model.repository.ExpenseTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour gérer la logique métier liée aux nature de frais (`ExpenseType`).
 *
 *  TODO : Ajouter la méthode d'ajout d'une nature de frais
 *
 * @author Marjory PRIN
 */
@Service
public class ExpenseTypeServiceImpl implements ExpenseTypeService {

    @Autowired
    private ExpenseTypeRepository expenseTypeRepository;

    /**
     * Récupère toutes les natures de frais.
     *
     * @return Une liste d'entités `ExpenseType`.
     */
    public List<ExpenseType> extractAllExpenseTypes() {
        return (List<ExpenseType>) expenseTypeRepository.findAll();
    }

    /**
     * Récupère une nature de frais par son identifiant unique.
     *
     * @param id L'identifiant unique de la nature de frais à récupérer.
     * @return L'entité `ExpenseType` correspondant à l'identifiant.
     */
    public ExpenseType extractExpenseTypeById(Long id) {
        return expenseTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nature de frais introuvable pour l'ID : " + id));
    }

    /**
     * Récupère une nature de frais par son nom.
     *
     * @param name Le nom de la nature de frais à récupérer.
     * @return L'entité `ExpenseType` correspondant au nom donné.
     */
    public ExpenseType extractExpenseTypeByName(String name) {
        return expenseTypeRepository.findByName(name);
    }
}
