package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.exception.FunctionalException;
import com.diginamic.mission_note_de_frais.model.entity.Expense;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;
import com.diginamic.mission_note_de_frais.model.repository.ExpenseReportRepository;
import com.diginamic.mission_note_de_frais.model.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implémentation du service pour gérer la logique métier liée aux lignes de frais (`Expense`).
 *
 * @author Marjory PRIN
 */
@Service
public class ExpenseServiceImpl implements ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private ExpenseReportRepository expenseReportRepository;

    /**
     * Récupère une ligne de frais par son identifiant.
     *
     * @param id L'identifiant unique de la ligne de frais à récupérer.
     * @return L'entité `Expense` correspondant à l'identifiant.
     * @throws EntityNotFoundException si aucune ligne de frais n'est trouvée pour l'identifiant fourni.
     */
    @Override
    public Expense extractExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ligne de frais introuvable pour l'ID : " + id));
    }

    /**
     * Récupère toutes les lignes de frais associées à une note de frais spécifique.
     *
     * @param expenseReport La note de frais pour laquelle récupérer les lignes de frais.
     * @return Une liste d'entités `Expense` associées à la note de frais.
     */
    @Override
    public List<Expense> extractExpensesByExpenseReport(ExpenseReport expenseReport) {
        return expenseRepository.findByExpenseReport(expenseReport);
    }

    /**
     * Insère une nouvelle ligne de frais dans la base de données.
     * <p>
     * TODO: Implémenter la règle métier pour la date (besoin de la mission)
     * La date est comprise entre la date de début de mission et peut-être postérieure à la date de fin de mission
     * </p>
     *
     * @param expense L'entité `Expense` à insérer.
     * @return `true` si l'insertion a été réussie, `false` sinon.
     * @throws FunctionalException si une erreur métier survient lors de l'insertion de la ligne de frais.
     */
    @Override
    public boolean insertExpense(Expense expense) throws FunctionalException {
        if(expense.getAmount() <= 0) {
            throw new FunctionalException("Le montant de la dépense doit être supérieur à 0");
        }
        if(expense.getTax() < 0) {
            throw new FunctionalException("Le pourcentage de TVA doit être égal ou supérieur à 0");
        }
        expenseRepository.save(expense);
        return true;
    }

    /**
     * Met à jour une ligne de frais existante.
     * <p>
     * TODO: Implémenter la règle métier pour la date (besoin de la mission)
     * La date est comprise entre la date de début de mission et peut-être postérieure à la date de fin de mission
     * </p>
     *
     * @param expense L'entité `Expense` contenant les nouvelles informations à mettre à jour.
     * @return `true` si la mise à jour a réussi, `false` sinon.
     * @throws FunctionalException si une erreur métier survient lors de la mise à jour de la ligne de frais.
     */
    @Override
    public boolean updateExpense(Expense expense) throws FunctionalException {
        Expense expenseFromDB = expenseRepository.findById(expense.getId())
                .orElseThrow(() -> new EntityNotFoundException("La ligne de frais avec l'ID " + expense.getId() + " n'a pas été trouvée"));
        if(expense.getAmount() <= 0) {
            throw new FunctionalException("Le montant de la dépense doit être supérieur à 0");
        }
        if(expense.getTax() < 0) {
            throw new FunctionalException("Le pourcentage de TVA doit être égal ou supérieur à 0");
        }
        expenseFromDB.setDate(expense.getDate());
        expenseFromDB.setType(expense.getType());
        expenseFromDB.setDescription(expense.getDescription());
        expenseFromDB.setAmount(expense.getAmount());
        expenseFromDB.setTax(expense.getTax());
        expenseRepository.save(expenseFromDB);
        return true;
    }

    /**
     * Supprime une ligne de frais en fonction de son identifiant unique.
     *
     * @param id L'identifiant unique de la ligne de frais à supprimer.
     * @return `true` si la suppression a réussi, `false` sinon.
     * @throws EntityNotFoundException si aucune ligne de frais n'est trouvée pour l'identifiant.
     */
    @Override
    public boolean deleteExpense(Long id) {
        Expense expenseFromDB = expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("La ligne de frais avec l'ID " + id + " n'a pas été trouvée"));
        expenseRepository.delete(expenseFromDB);
        return true;
    }

}
