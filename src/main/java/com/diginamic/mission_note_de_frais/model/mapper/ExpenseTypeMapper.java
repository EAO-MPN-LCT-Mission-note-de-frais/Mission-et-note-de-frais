package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.ExpenseTypeDTO;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseType;
import org.springframework.stereotype.Component;

@Component
public class ExpenseTypeMapper {

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

    /**
     * Convertit un DTO `ExpenseTypeDTO` en une entité `ExpenseType`.
     *
     * @param expenseTypeDTO Le DTO `ExpenseTypeDTO` à convertir.
     * @return Une instance de `ExpenseType` contenant les données de l'entité.
     * @throws IllegalArgumentException si le DTO `ExpenseTypeDTO` est null.
     */
    public ExpenseType toEntity(ExpenseTypeDTO expenseTypeDTO) {
        if (expenseTypeDTO == null) {
            throw new IllegalArgumentException("Le DTO ExpenseTypeDTO ne peut pas être null");
        }
        ExpenseType expenseType = new ExpenseType();
        expenseType.setName(expenseTypeDTO.getName());
        return expenseType;
    }

}
