package com.diginamic.mission_note_de_frais.controller;

import com.diginamic.mission_note_de_frais.exception.FunctionalException;
import com.diginamic.mission_note_de_frais.model.dto.ExpenseDTO;
import com.diginamic.mission_note_de_frais.model.entity.Expense;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;
import com.diginamic.mission_note_de_frais.model.mapper.ExpenseMapper;
import com.diginamic.mission_note_de_frais.service.ExpenseReportServiceImpl;
import com.diginamic.mission_note_de_frais.service.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrôleur REST pour gérer les opérations liées aux lignes de frais.
 *
 * <p>Ce contrôleur fournit des points d'accès RESTful pour effectuer des opérations CRUD
 * sur les lignes de frais.</p>
 *
 *  * TODO : Ajouter la méthode POST, PUT et DELETE
 *
 * @author Marjory PRIN
 */
@RestController
@RequestMapping("/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseServiceImpl expenseService;

    @Autowired
    private ExpenseReportServiceImpl expenseReportService;

    @Autowired
    private ExpenseMapper expenseMapper;

    /**
     * Récupère une nature de frais par son id.
     *
     * @param id l'identifiant de la ligne de frais
     * @return La ligne de frais correspondante à l'id donné
     */
    @GetMapping("/{id}")
    public ExpenseDTO getExpenseById(@PathVariable Long id) {
        return expenseMapper.toDTO(expenseService.extractExpenseById(id));
    }

    /**
     * Récupère toutes les lignes de frais pour une note de frais donnée.
     *
     * @param id l'identifiant de la note de frais
     * @return La liste de toutes les lignes de frais correspondante à la note de frais donnée
     */
    @GetMapping("/expense-report/{id}")
    public List<ExpenseDTO> getExpenseByExpenseReportId(@PathVariable Long id) {
        ExpenseReport expenseReport = expenseReportService.getExpenseReportById(id);
        return expenseService.extractExpensesByExpenseReport(expenseReport)
                .stream()
                .map(expense -> expenseMapper.toDTO(expense))
                .toList();
    }

    /**
     * Insère une nouvelle ligne de frais.
     *
     * @param newExpense Le DTO de Expense à insérer.
     * @param expenseReportId L'identifiant de la note de frais auquel cette dépense est associée.
     * @return ResponseEntity avec le statut HTTP et un message.
     */
    @PostMapping
    public ResponseEntity<String> insertExpense(@RequestBody ExpenseDTO newExpense, @RequestParam Long expenseReportId) throws FunctionalException {
        try {
            ExpenseReport expenseReport = expenseReportService.getExpenseReportById(expenseReportId);
            Expense expense = expenseMapper.toEntity(newExpense);
            expense.setExpenseReport(expenseReport);

            boolean result = expenseService.insertExpense(expense);

            if (result) {
                return new ResponseEntity<String>("Dépense insérée avec succès", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Echec de l'insertion : La dépense n'a pas pu être insérée pour une raison inconnue", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (FunctionalException e) {
            return new ResponseEntity<>("Erreur de validation (400) : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur interne du serveur : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
