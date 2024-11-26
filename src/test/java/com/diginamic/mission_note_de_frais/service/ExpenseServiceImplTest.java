package com.diginamic.mission_note_de_frais.service;

import com.diginamic.mission_note_de_frais.exception.FunctionalException;
import com.diginamic.mission_note_de_frais.model.entity.Expense;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseType;
import com.diginamic.mission_note_de_frais.model.repository.ExpenseRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;

    @Test
    void testExtractExpenseById_Success() {
        Expense expense = new Expense();
        expense.setId(1L);

        when(expenseRepository.findById(1L)).thenReturn(Optional.of(expense));

        Expense result = expenseService.extractExpenseById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testExtractExpenseById_NotFound() {
        when(expenseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            expenseService.extractExpenseById(1L);
        });
    }

    @Test
    void testExtractExpensesByExpenseReport() {
        ExpenseReport expenseReport = new ExpenseReport();
        Expense expense1 = new Expense();
        Expense expense2 = new Expense();
        List<Expense> expenses = Arrays.asList(expense1, expense2);

        when(expenseRepository.findByExpenseReport(expenseReport)).thenReturn(expenses);

        List<Expense> result = expenseService.extractExpensesByExpenseReport(expenseReport);

        assertNotNull(result);
        assertEquals(2, result.size());
    }

    @Test
    void testInsertExpense_Success() throws FunctionalException {
        Expense expense = new Expense();
        expense.setDate(LocalDate.now());
        expense.setType(new ExpenseType("Transport"));
        expense.setAmount(45.5);
        expense.setTax(20.0);

        when(expenseRepository.save(any(Expense.class))).thenReturn(expense);

        boolean result = expenseService.insertExpense(expense);

        assertTrue(result);
        verify(expenseRepository, times(1)).save(expense);
    }

    @Test
    void testInsertExpense_FunctionalException() {
        Expense expense = new Expense();
        expense.setDate(LocalDate.now());
        expense.setType(new ExpenseType("Transport"));
        expense.setAmount(0.0);
        expense.setTax(20.0);

        assertThrows(FunctionalException.class, () -> {
            expenseService.insertExpense(expense);
        });
    }
}
