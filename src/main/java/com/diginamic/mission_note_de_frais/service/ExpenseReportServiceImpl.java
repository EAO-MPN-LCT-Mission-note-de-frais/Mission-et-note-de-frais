package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;
import com.diginamic.mission_note_de_frais.model.repository.ExpenseReportRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service pour gérer la logique métier liée aux note de frais (`ExpenseReport`).
 *
 * TODO : Ajouter l'extract par collaborateur et par état +  l'ajout, la modif (état) et la suppression
 *
 * @author Marjory PRIN
 */
@Service
public class ExpenseReportServiceImpl implements ExpenseReportService {

    @Autowired
    private ExpenseReportRepository expenseReportRepository;

    /**
     * Récupère toutes les note de frais.
     *
     * @return Une liste d'entités `ExpenseReport`.
     */
    public List<ExpenseReport> extractAllExpenseReport() {
        return (List<ExpenseReport>) expenseReportRepository.findAll();
    }

    /**
     * Récupère une note de frais par son identifiant.
     *
     * @param id L'identifiant unique de la note de frais à récupérer.
     * @return L'entité `ExpenseReport` correspondant à l'identifiant.
     * @throws EntityNotFoundException si aucune note de frais n'est trouvée pour l'identifiant fourni.
     */
    public ExpenseReport getExpenseReportById(Long id) {
        return expenseReportRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Note de frais introuvable pour l'ID : " + id));
    }
}
