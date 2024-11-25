package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.ExpenseDTO;
import com.diginamic.mission_note_de_frais.model.entity.Expense;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseType;
import com.diginamic.mission_note_de_frais.service.ExpenseServiceImpl;
import com.diginamic.mission_note_de_frais.service.ExpenseTypeServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ExpenseMapper {

    @Autowired
    private ExpenseServiceImpl expenseService;

    @Autowired
    private ExpenseTypeServiceImpl expenseTypeService;

    /**
     * Convertit une entité `Expense` en un DTO `ExpenseDTO`.
     *
     * @param expense L'entité `Expense` à convertir.
     * @return Une instance de `ExpenseDTO` contenant les données formatées pour l'affichage.
     * @throws IllegalArgumentException si l'entité `Expense` est null.
     */
    public ExpenseDTO toDTO(Expense expense) {
        if (expense == null) {
            throw new IllegalArgumentException("L'entité Expense ne peut pas être null");
        }
        ExpenseDTO expenseDTO = new ExpenseDTO();
        expenseDTO.setDate(expense.getDate());
        expenseDTO.setExpenseType(expense.getType().getName());
        expenseDTO.setDescription(expense.getDescription());
        expenseDTO.setAmount(expense.getAmount());
        expenseDTO.setTax(expense.getTax());
        return expenseDTO;
    }

    /**
     * Convertit un DTO `ExpenseDTO` en une entité `Expense`.
     *
     * @param expenseDTO Le DTO `ExpenseDTO` à convertir.
     * @return Une nouvelle instance de l'entité `Expense` correspondant au DTO.
     * @throws IllegalArgumentException si le DTO est null ou si des dépendances obligatoires manquent.
     */
    public Expense toEntity(ExpenseDTO expenseDTO) {
        if (expenseDTO == null) {
            throw new IllegalArgumentException("Le DTO ne peut pas être null");
        }
        Expense expense = new Expense();
        expense.setDate(expenseDTO.getDate());
        expense.setDescription(expenseDTO.getDescription());
        expense.setAmount(expenseDTO.getAmount());
        expense.setTax(expenseDTO.getTax());
        ExpenseType type = new ExpenseType();
        type.setName(expenseDTO.getExpenseType());
        expense.setType(type);
        ExpenseType t = expenseTypeService.extractExpenseTypeByName(expenseDTO.getExpenseType());
        if (t != null) {
            t.setName(type.getName());
            expense.setType(t);
        }
        return expense;
    }
}
