package com.diginamic.mission_note_de_frais.model.dto;

import lombok.Data;

@Data
public class SimpleExpenseReportDTO {
  private Long id;
  private double amount;
  private StatusDTO status;
}
