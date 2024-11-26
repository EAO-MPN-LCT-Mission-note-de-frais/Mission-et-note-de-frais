package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.SimpleExpenseReportDTO;
import com.diginamic.mission_note_de_frais.model.entity.Expense;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.function.Function;

@Controller
@RequiredArgsConstructor
public class SimpleExpenseReportMapper implements Function<ExpenseReport, SimpleExpenseReportDTO> {

  private final StatusMapper statusMapper;

  @Override
  public SimpleExpenseReportDTO apply(ExpenseReport report) {
    Assert.notNull(report, "The expense report cannot be null");

    var totalExpenses = report.getExpenses()
        .stream()
        .mapToDouble(Expense::getAmount).sum();

    var simpleReport = new SimpleExpenseReportDTO();
    simpleReport.setAmount(totalExpenses);
    simpleReport.setId(report.getId());

    Optional.ofNullable(report.getStatus())
        .map(statusMapper)
        .ifPresent(simpleReport::setStatus);

    return simpleReport;
  }
}
