package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.ExpenseTypeDTO;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseType;
import com.diginamic.mission_note_de_frais.service.ExpenseTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ExpenseTypeMapper {

    @Autowired
    private ExpenseTypeServiceImpl expenseTypeService;

    /**
     * Convertit une entité `ExpenseType` en un DTO `ExpenseTypeDTO`.
     *
     * @param expenseType L'entité `ExpenseType` à convertir.
     * @return Une instance de `ExpenseTypeDTO` contenant les données formatées pour l'affichage.
     * @throws IllegalArgumentException si l'entité `Expense` est null.
     */
    public ExpenseTypeDTO toDto(ExpenseType expenseType) {
        if (expenseType == null) {
            throw new IllegalArgumentException("L'entité ExpenseType ne peut pas être null");
        }
        ExpenseTypeDTO expenseTypeDTO = new ExpenseTypeDTO();
        expenseTypeDTO.setName(expenseType.getName());
        return expenseTypeDTO;
    }
}
