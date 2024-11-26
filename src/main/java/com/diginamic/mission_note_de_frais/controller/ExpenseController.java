package com.diginamic.mission_note_de_frais.controller;

import com.diginamic.mission_note_de_frais.model.dto.ExpenseDTO;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;
import com.diginamic.mission_note_de_frais.model.mapper.ExpenseMapper;
import com.diginamic.mission_note_de_frais.service.ExpenseReportServiceImpl;
import com.diginamic.mission_note_de_frais.service.ExpenseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
     * @param expenseDTO Le DTO de Expense à insérer.
     * @return ResponseEntity avec le statut HTTP et l'entité insérée.
     */


}
