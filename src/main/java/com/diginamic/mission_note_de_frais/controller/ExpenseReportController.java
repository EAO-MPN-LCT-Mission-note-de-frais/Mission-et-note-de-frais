package com.diginamic.mission_note_de_frais.controller;

import com.diginamic.mission_note_de_frais.exception.FunctionalException;
import com.diginamic.mission_note_de_frais.model.dto.ExpenseDTO;
import com.diginamic.mission_note_de_frais.model.dto.MissionDTO;
import com.diginamic.mission_note_de_frais.model.dto.SimpleExpenseReportDTO;
import com.diginamic.mission_note_de_frais.model.entity.Expense;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;
import com.diginamic.mission_note_de_frais.model.entity.Mission;
import com.diginamic.mission_note_de_frais.model.mapper.ExpenseMapper;
import com.diginamic.mission_note_de_frais.model.mapper.MissionMapper;
import com.diginamic.mission_note_de_frais.model.mapper.SimpleExpenseReportMapper;
import com.diginamic.mission_note_de_frais.service.ExpenseReportServiceImpl;
import com.diginamic.mission_note_de_frais.service.ExpenseServiceImpl;
import com.diginamic.mission_note_de_frais.service.MissionServiceImpl;
import com.diginamic.mission_note_de_frais.util.PdfGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Contrôleur REST pour gérer les opérations liées aux notes de frais.
 *
 * <p>Ce contrôleur fournit des points d'accès RESTful pour effectuer des opérations CRUD
 * sur les notes de frais.</p>
 */
@RestController
@RequestMapping("/expense-reports")
public class ExpenseReportController {

    @Autowired
    private ExpenseReportServiceImpl expenseReportService;

    @Autowired
    private SimpleExpenseReportMapper expenseReportMapper;

    @Autowired
    private MissionServiceImpl missionService;

    @Autowired
    private MissionMapper missionMapper;

    @Autowired
    private ExpenseServiceImpl expenseService;

    @Autowired
    private ExpenseMapper expenseMapper;

    @Autowired
    private PdfGenerator pdfGenerator;

    /**
     * Récupère une note de frais par son id.
     *
     * @param id l'identifiant de la note de frais
     * @return La note de frais correspondante à l'id donné
     */
    @GetMapping("/{id}")
    public SimpleExpenseReportDTO getExpenseReportById(@PathVariable Long id) {
        return expenseReportMapper.apply(expenseReportService.getExpenseReportById(id));
    }

    /**
     * Exporter une note de frais sous forme de PDF.
     *
     * @param id l'identifiant de la note de frais
     * @return Un fichier PDF représentant la note de frais avec les informations de mission et le tableau des dépenses
     */
    @GetMapping("/{id}/export-pdf")
    public ResponseEntity<byte[]> exportExpenseReportToPdf(@PathVariable Long id) {
        try {
            // Récupération des DTO
            ExpenseReport expenseReport = expenseReportService.getExpenseReportById(id);
            SimpleExpenseReportDTO expenseReportDTO = expenseReportMapper.apply(expenseReport);
            MissionDTO missionDTO = missionService.getMissionById(expenseReport.getMission().getId());
            List<Expense> expenses = expenseService.extractExpensesByExpenseReport(expenseReport);
            List<ExpenseDTO> expenseDTOs = expenses.stream()
                    .map(expenseMapper::toDTO)
                    .collect(Collectors.toList());

            // Générer le PDF
            byte[] pdfBytes = PdfGenerator.generatePdf(expenseReportDTO, missionDTO, expenseDTOs);

            // Configurer les entêtes de la réponse
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("filename", "expense_report_" + id + ".pdf");

            return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Insère une nouvelle note de frais.
     *
     * @param newExpenseReport Le DTO de ExpenseReport à insérer.
     * @param missionId L'identifiant de la mission auquel cette note de frais est associée.
     * @return ResponseEntity avec le statut HTTP et un message.
     */
    @PostMapping
    public ResponseEntity<String> insertExpenseReport(@RequestBody SimpleExpenseReportDTO newExpenseReport, @RequestParam int missionId) {
        try {
            // Récupérer la mission
            Mission mission = missionMapper.toEntity(missionService.getMissionById(missionId));
            ExpenseReport expenseReport = expenseReportMapper.toEntity(newExpenseReport);
            expenseReport.setMission(mission);
            boolean result = expenseReportService.addExpenseReport(expenseReport);

            if (result) {
                return new ResponseEntity<>("Note de frais insérée avec succès", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Échec de l'insertion : La note de frais n'a pas pu être insérée pour une raison inconnue", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (FunctionalException e) {
            return new ResponseEntity<>("Erreur de validation (400) : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur interne du serveur : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    /**
     * Met à jour une note de frais existante.
     *
     * @param id L'identifiant de la note de frais à mettre à jour.
     * @param updatedExpenseReport Le DTO de ExpenseReport avec les nouvelles données.
     * @return ResponseEntity avec le statut HTTP et un message.
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateExpenseReport(@PathVariable Long id, @RequestBody SimpleExpenseReportDTO updatedExpenseReport) {
        try {
            ExpenseReport expenseReport = expenseReportMapper.toEntity(updatedExpenseReport);
            expenseReport.setId(id);

            boolean result = expenseReportService.updateExpenseReport(expenseReport);

            if (result) {
                return new ResponseEntity<>("Note de frais mise à jour avec succès", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Échec de la mise à jour : La note de frais n'a pas pu être mise à jour pour une raison inconnue", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur interne du serveur : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Supprime une note de frais existante.
     *
     * @param id L'identifiant de la note de frais à supprimer.
     * @return ResponseEntity avec le statut HTTP et un message.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpenseReport(@PathVariable Long id) {
        try {
            boolean result = expenseReportService.deleteExpenseReport(id);
            if (result) {
                return new ResponseEntity<>("Note de frais supprimée avec succès", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Échec de la suppression : La note de frais n'a pas pu être supprimée pour une raison inconnue", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur interne du serveur : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}