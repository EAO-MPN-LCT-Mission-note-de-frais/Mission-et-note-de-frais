package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.exception.FunctionalException;
import com.diginamic.mission_note_de_frais.model.entity.Expense;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;
import com.diginamic.mission_note_de_frais.model.entity.Mission;
import com.diginamic.mission_note_de_frais.model.entity.Status;
import com.diginamic.mission_note_de_frais.model.repository.ExpenseReportRepository;
import com.diginamic.mission_note_de_frais.model.repository.MissionRepository;
import com.diginamic.mission_note_de_frais.model.repository.StatusRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Service pour gérer la logique métier liée aux notes de frais (`ExpenseReport`).
 *
 * @author Marjory PRIN
 */
@Service
public class ExpenseReportServiceImpl implements ExpenseReportService {

    @Autowired
    private ExpenseReportRepository expenseReportRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private MissionRepository missionRepository;

    /**
     * Récupère une note de frais par son identifiant.
     *
     * @param id L'identifiant unique de la note de frais à récupérer.
     * @return L'entité `ExpenseReport` correspondant à l'identifiant.
     * @throws EntityNotFoundException si aucune note de frais n'est trouvée pour l'identifiant fourni.
     */
    @Override
    public ExpenseReport getExpenseReportById(Long id) {
        return expenseReportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note de frais introuvable pour l'ID : " + id));
    }

    /**
     * Insert une nouvelle note de frais en base
     *
     * @param expenseReport L'entité `Expense` à insérer.
     * @return `true` si l'insertion a été réussie, `false` sinon.
     * @throws FunctionalException si une erreur métier survient lors de l'insertion de la note de frais.
     */
    @Override
    public boolean addExpenseReport(ExpenseReport expenseReport) throws FunctionalException {
        // Vérification de la mission associée
        int missionId = expenseReport.getMission().getId();
        Mission mission = missionRepository.findById(missionId)
                .orElseThrow(() -> new EntityNotFoundException("Mission introuvable pour l'ID : " + missionId));
        expenseReport.setMission(mission);
        LocalDate endDate = mission != null ? mission.getEndDate() : null;

        if (mission == null || endDate == null || endDate.isAfter(LocalDate.now())) {
            throw new FunctionalException("La mission doit être terminée (date de fin atteinte) pour ajouter une note de frais");
        }

        // Calcul du montant total
        double totalAmount = expenseReport.getExpenses().stream()
                .mapToDouble(Expense::getAmount)
                .sum();

        // Utilisation d'Optional pour gérer l'absence du statut initial
        Status initialStatus = statusRepository.findByName(Status.MissionStatus.INITIALE)
                .orElseThrow(() -> new EntityNotFoundException("Statut initial introuvable")
        );
        expenseReport.setStatus(initialStatus);
        expenseReportRepository.save(expenseReport);
        return true;
    }

    /**
     * Mettre à jour le statut de la note de frais
     *
     * @param expenseReport L'entité `Expense` contenant les nouvelles informations à mettre à jour.
     * @return `true` si la mise à jour a été réussie, `false` sinon.
     */
    @Override
    public boolean updateExpenseReport(ExpenseReport expenseReport) {
        ExpenseReport existingReport= expenseReportRepository.findById(expenseReport.getId())
                .orElseThrow(() -> new EntityNotFoundException("Note de frais introuvable pour l'ID : " + expenseReport.getId()));
        Status existingStatus = existingReport.getStatus();
        Status newStatus = expenseReport.getStatus();
        if (existingStatus != null && !existingStatus.getName().equals(newStatus.getName())) {
            existingReport.setStatus(newStatus);
        }
        expenseReportRepository.save(existingReport);
        return true;
    }

    /**
     * Supprime une note de frais en fonction de son identifiant unique.
     *
     * @param id L'identifiant unique de la note de frais à supprimer.
     * @return `true` si la suppression a réussi, `false` sinon.
     * @throws EntityNotFoundException si aucune ligne de frais n'est trouvée pour l'identifiant.
     * @throws FunctionalException si une erreur métier survient lors de la suppression de la note de frais.
     */
    @Override
    public boolean deleteExpenseReport(Long id) throws FunctionalException{
        ExpenseReport expenseReportFromDB = expenseReportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note de frais non trouvée pour l'ID : " + id));
        String status = expenseReportFromDB.getStatus().getName().name();
        if (!status.equals("INITIALE") && !status.equals("REJETEE")) {
            throw new FunctionalException("La note de frais doit être au statut INITIAL ou REJETÉE pour la supprimer");
        }
        expenseReportRepository.delete(expenseReportFromDB);
        return true;
    }
}


