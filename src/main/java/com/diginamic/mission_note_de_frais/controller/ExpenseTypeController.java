package com.diginamic.mission_note_de_frais.controller;

import com.diginamic.mission_note_de_frais.model.dto.ExpenseTypeDTO;
import com.diginamic.mission_note_de_frais.model.mapper.ExpenseTypeMapper;
import com.diginamic.mission_note_de_frais.service.ExpenseTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Contrôleur REST pour gérer les opérations liées aux nature de frais.
 *
 * <p>Ce contrôleur fournit des points d'accès RESTful pour effectuer des opérations CRUD
 * sur les natures de frais.</p>
 *
 * TODO : Ajouter la méthode POST
 *
 * @author Marjory PRIN
 */
@RestController
@RequestMapping("/api/expense-types")
public class ExpenseTypeController {

    @Autowired
    private ExpenseTypeServiceImpl expenseTypeService;

    @Autowired
    private ExpenseTypeMapper expenseTypeMapper;

    /**
     * Récupère toutes les natures de frais.
     *
     * @return la liste de toutes les natures de frais
     */
    @GetMapping
    public List<ExpenseTypeDTO> getAllExpenseTypes() {
        return expenseTypeService.extractAllExpenseTypes()
                .stream()
                .map(expenseType -> expenseTypeMapper.toDto(expenseType))
                .toList();
    }

    /**
     * Récupère une nature de frais par son ID.
     *
     * @param id l'identifiant de la nature de frais
     * @return La nature de frais correspondante à l'id donné
     */
    @GetMapping("/{id}")
    public ExpenseTypeDTO getExpenseTypeById(@PathVariable Long id) {
        return expenseTypeMapper.toDto(expenseTypeService.extractExpenseTypeById(id));
    }

    /**
     * Récupère une nature de frais par son nom.
     *
     * @param name le nom de la nature de frais
     * @return La nature de frais correspondante au nom donné
     */
    @GetMapping("/name/{name}")
    public ExpenseTypeDTO getExpenseTypeByName(@PathVariable String name) {
        return expenseTypeMapper.toDto(expenseTypeService.extractExpenseTypeByName(name));
    }
}
