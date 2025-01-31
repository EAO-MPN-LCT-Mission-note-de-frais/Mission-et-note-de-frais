package com.diginamic.mission_note_de_frais.controller;

import com.diginamic.mission_note_de_frais.exception.FunctionalException;
import com.diginamic.mission_note_de_frais.model.dto.ApiResponseDTO;
import com.diginamic.mission_note_de_frais.model.dto.ExpenseDTO;
import com.diginamic.mission_note_de_frais.model.entity.Expense;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;
import com.diginamic.mission_note_de_frais.model.mapper.ExpenseMapper;
import com.diginamic.mission_note_de_frais.service.ExpenseReportServiceImpl;
import com.diginamic.mission_note_de_frais.service.ExpenseServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Contrôleur REST pour gérer les opérations liées aux lignes de frais.
 *
 * <p>Ce contrôleur fournit des points d'accès RESTful pour effectuer des opérations CRUD
 * sur les lignes de frais.</p>
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
    public ResponseEntity<ApiResponseDTO> getExpenseById(@PathVariable Long id) throws EntityNotFoundException {
        ExpenseDTO expenseDTO = expenseMapper.toDTO(expenseService.extractExpenseById(id));
        ApiResponseDTO response = new ApiResponseDTO(
                "Success",
                "Dépense récupérée avec succès",
                HttpStatus.OK.value(),
                LocalDateTime.now(),
                expenseDTO
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Récupère toutes les lignes de frais pour une note de frais donnée.
     *
     * @param id l'identifiant de la note de frais
     * @return La liste de toutes les lignes de frais correspondante à la note de frais donnée
     */
    @GetMapping("/expense-report/{id}")
    public ResponseEntity<ApiResponseDTO> getExpenseByExpenseReportId(@PathVariable Long id) throws EntityNotFoundException {
        ExpenseReport expenseReport = expenseReportService.getExpenseReportById(id);
        List<ExpenseDTO> expenseDTOs = expenseService.extractExpensesByExpenseReport(expenseReport)
                .stream()
                .map(expenseMapper::toDTO)
                .toList();
        ApiResponseDTO response = new ApiResponseDTO(
                "Success",
                "Dépenses récupérées avec succès",
                HttpStatus.OK.value(),
                LocalDateTime.now(),
                expenseDTOs
        );
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    /**
     * Insère une nouvelle ligne de frais.
     *
     * @param newExpense Le DTO de Expense à insérer.
     * @param expenseReportId L'identifiant de la note de frais auquel cette dépense est associée.
     * @return ResponseEntity avec le statut HTTP et un message.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO> insertExpense(@RequestBody ExpenseDTO newExpense, @RequestParam Long expenseReportId) throws FunctionalException, EntityNotFoundException {
        // Définir l'expenseReportId dans newExpense
        newExpense.setExpenseReportId(expenseReportId);
        // Récupérer le ExpenseReport
        ExpenseReport expenseReport = expenseReportService.getExpenseReportById(expenseReportId);

        // Mapper le DTO à l'entité et associer le ExpenseReport
        Expense expense = expenseMapper.toEntity(newExpense);
        expense.setExpenseReport(expenseReport);

        boolean result = expenseService.insertExpense(expense);

        if (result) {
            ApiResponseDTO response = new ApiResponseDTO(
                    "Success",
                    "Dépense insérée avec succès",
                    HttpStatus.OK.value(),
                    LocalDateTime.now(),
                    newExpense
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponseDTO response = new ApiResponseDTO(
                    "Internal Server Error",
                    "Échec de l'insertion : La dépense n'a pas pu être insérée pour une raison inconnue",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    LocalDateTime.now(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Met à jour une ligne de frais existante.
     *
     * @param id L'identifiant de la dépense à mettre à jour.
     * @param updatedExpense Le DTO de Expense avec les nouvelles données.
     * @return ResponseEntity avec le statut HTTP et un message.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> updateExpense(@PathVariable Long id, @RequestBody ExpenseDTO updatedExpense) throws FunctionalException, EntityNotFoundException {
        // Mapper le DTO à l'entité
        Expense expense = expenseMapper.toEntity(updatedExpense);
        expense.setId(id);

        boolean result = expenseService.updateExpense(expense);

        if (result) {
            ApiResponseDTO response = new ApiResponseDTO(
                    "Success",
                    "Dépense mise à jour avec succès",
                    HttpStatus.OK.value(),
                    LocalDateTime.now(),
                    updatedExpense
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponseDTO response = new ApiResponseDTO(
                    "Internal Server Error",
                    "Échec de la mise à jour : La dépense n'a pas pu être mise à jour pour une raison inconnue",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    LocalDateTime.now(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Supprime une ligne de frais existante.
     *
     * @param id L'identifiant de la dépense à supprimer.
     * @return ResponseEntity avec le statut HTTP et un message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO> deleteExpense(@PathVariable Long id) throws FunctionalException, EntityNotFoundException {
        boolean result = expenseService.deleteExpense(id);
        if (result) {
            ApiResponseDTO response = new ApiResponseDTO(
                    "Success",
                    "Dépense supprimée avec succès",
                    HttpStatus.OK.value(),
                    LocalDateTime.now(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponseDTO response = new ApiResponseDTO(
                    "Internal Server Error",
                    "Échec de la suppression : La dépense n'a pas pu être supprimée pour une raison inconnue",
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    LocalDateTime.now(),
                    null
            );
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
