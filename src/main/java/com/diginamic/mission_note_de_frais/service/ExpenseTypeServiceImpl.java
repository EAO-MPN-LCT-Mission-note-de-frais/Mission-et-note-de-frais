package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.exception.FunctionalException;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseType;
import com.diginamic.mission_note_de_frais.model.repository.ExpenseTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour gérer la logique métier liée aux nature de frais (`ExpenseType`).
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

    /**
     * Insère une nouvelle nature de frais dans le système.
     *
     * @param expenseType L'entité `ExpenseType` à insérer.
     * @return `true` si l'insertion a réussi, `false` sinon.
     * @throws FunctionalException si une erreur métier survient lors de l'insertion de la nature de frais.
     */
    public boolean insertExpenseType(ExpenseType expenseType) throws FunctionalException {
        if (expenseTypeRepository.findByName(expenseType.getName()) != null) {
            throw new FunctionalException("Une nature de frais avec ce nom existe déjà.");
        }
        ExpenseType newExpenseType = new ExpenseType();
        newExpenseType.setName(expenseType.getName());
        expenseTypeRepository.save(newExpenseType);
        return true;
    }

    /**
     * Met à jour une nature de frais existante.
     *
     * @param expenseType L'entité `ExpenseType` contenant les nouvelles informations à mettre à jour.
     * @return `true` si la mise à jour a réussi, `false` sinon.
     * @throws FunctionalException si une erreur métier survient lors de la mise à jour de la nature de frais.
     */
    public boolean updateExpenseType(ExpenseType expenseType) throws FunctionalException {
        ExpenseType expenseTypeFromDB = expenseTypeRepository.findById(expenseType.getId())
                .orElseThrow(() -> new EntityNotFoundException("Nature de frais introuvable pour l'ID : " + expenseType.getId()));


        // Vérifier si le nouveau nom est déjà utilisé par un autre enregistrement
        ExpenseType expenseTypeWithSameName = expenseTypeRepository.findByName(expenseType.getName());
        if (expenseTypeWithSameName != null && !expenseTypeWithSameName.getId().equals(expenseType.getId())) {
            throw new FunctionalException("Une nature de frais avec ce nom existe déjà.");
        }

        expenseTypeFromDB.setName(expenseType.getName());
        expenseTypeRepository.save(expenseTypeFromDB);

        return true;
    }

    /**
     * Supprime une nature de frais en fonction de son identifiant unique.
     *
     * @param id L'identifiant unique de la nature de frais à supprimer.
     * @return `true` si la suppression a réussi, `false` sinon.
     */
    public boolean deleteExpenseType(Long id) throws FunctionalException {
        ExpenseType expenseTypeFromDB = expenseTypeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Nature de frais introuvable pour l'ID : " + id));

        // Vérifier si l'ExpenseType est associé à des dépenses
        if (!expenseTypeFromDB.getExpenses().isEmpty()) {
            throw new FunctionalException("Suppression impossible, la nature de frais est associée à des dépenses existantes.");
        }

        expenseTypeRepository.delete(expenseTypeFromDB);
        return true;
    }
}
