package com.diginamic.mission_note_de_frais.controller;

import com.diginamic.mission_note_de_frais.exception.FunctionalException;
import com.diginamic.mission_note_de_frais.model.dto.ExpenseTypeDTO;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseType;
import com.diginamic.mission_note_de_frais.model.mapper.ExpenseTypeMapper;
import com.diginamic.mission_note_de_frais.service.ExpenseTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/expense-types")
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

    /**
     * Ajoute une nouvelle nature de frais.
     *
     * @param newExpenseType l'objet DTO de la nature de frais à ajouter
     * @return ResponseEntity avec le statut HTTP et un message.
     */
    @PostMapping
    public ResponseEntity<String> addExpenseType(@RequestBody ExpenseTypeDTO newExpenseType) {
        try {
            ExpenseType expenseType = expenseTypeMapper.toEntity(newExpenseType);

            boolean result = expenseTypeService.insertExpenseType(expenseType);

            if (result) {
                return new ResponseEntity<>("Nature de frais insérée avec succès", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Echec de l'insertion : La nature de frais n'a pas pu être insérée pour une raison inconnue", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (FunctionalException e) {
            return new ResponseEntity<>("Erreur de validation (400) : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur interne du serveur : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Met à jour une nature de frais existante.
     *
     * @param id l'identifiant de la nature de frais à mettre à jour
     * @param expenseTypeDTO l'objet DTO contenant les nouvelles informations
     * @return Un message indiquant le succès ou l'échec de l'opération
     */
    @PutMapping("/{id}")
    public ResponseEntity<String> updateExpenseType(@PathVariable Long id, @RequestBody ExpenseTypeDTO expenseTypeDTO) {
        try {
            ExpenseType expenseType = expenseTypeMapper.toEntity(expenseTypeDTO);
            expenseType.setId(id);
            boolean result = expenseTypeService.updateExpenseType(expenseType);

            if (result) {
                return new ResponseEntity<>("Nature de frais mise à jour avec succès", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Echec de la mise à jour : La nature de frais n'a pas pu être mise à jour pour une raison inconnue", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (FunctionalException e) {
            return new ResponseEntity<>("Erreur de validation (400) : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur interne du serveur : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Supprime une nature de frais par son ID.
     *
     * @param id l'identifiant de la nature de frais à supprimer
     * @return Un message indiquant le succès ou l'échec de l'opération
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteExpenseType(@PathVariable Long id) {
        try {
            boolean result = expenseTypeService.deleteExpenseType(id);

            if (result) {
                return new ResponseEntity<>("Nature de frais supprimée avec succès", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Echec de la suppression : La nature de frais n'a pas pu être supprimée pour une raison inconnue", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (FunctionalException e) {
            return new ResponseEntity<>("Erreur de validation (400) : " + e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur interne du serveur : " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
