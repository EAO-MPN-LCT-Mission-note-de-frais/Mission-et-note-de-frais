package com.diginamic.mission_note_de_frais.model.mapper;

import com.diginamic.mission_note_de_frais.model.dto.ExpenseReportDTO;
import com.diginamic.mission_note_de_frais.model.entity.Expense;
import com.diginamic.mission_note_de_frais.model.entity.ExpenseReport;
import com.diginamic.mission_note_de_frais.model.entity.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;

import java.util.Optional;
import java.util.function.Function;

@Controller
@RequiredArgsConstructor
public class ExpenseReportMapper implements Function<ExpenseReport, ExpenseReportDTO> {

  private final StatusMapper statusMapper;

  @Override
  public ExpenseReportDTO apply(ExpenseReport report) {
    Assert.notNull(report, "The expense report cannot be null");

    var totalExpenses = report.getExpenses()
        .stream()
        .mapToDouble(Expense::getAmount).sum();

    var simpleReport = new ExpenseReportDTO();
    simpleReport.setAmount(totalExpenses);
    simpleReport.setId(report.getId());

    Optional.ofNullable(report.getStatus())
        .map(statusMapper)
        .ifPresent(simpleReport::setStatus);

    return simpleReport;
  }

  /**
   * Convertit un DTO `ExpenseReportDTO` en une entité `ExpenseReport`.
   *
   * @param expenseReportDTO Le DTO `ExpenseReportDTO` à convertir.
   * @return Une nouvelle instance de l'entité `ExpenseReport` correspondant au DTO.
   * @throws IllegalArgumentException si le DTO est null ou si des dépendances obligatoires manquent.
   */
  public ExpenseReport toEntity(ExpenseReportDTO expenseReportDTO) {
    if (expenseReportDTO == null) {
      throw new IllegalArgumentException("Le DTO ne peut pas être null");
    }
    ExpenseReport expenseReport = new ExpenseReport();
    expenseReport.setId(expenseReportDTO.getId());

    // Gestion du statut de la note de frais directement à partir du DTO
    if (expenseReportDTO.getStatus() != null) {
      Status status = new Status();
      status.setId(expenseReportDTO.getStatus().getId());
      status.setName(expenseReportDTO.getStatus().getName());
      expenseReport.setStatus(status);
    }

    return expenseReport;
  }
}
