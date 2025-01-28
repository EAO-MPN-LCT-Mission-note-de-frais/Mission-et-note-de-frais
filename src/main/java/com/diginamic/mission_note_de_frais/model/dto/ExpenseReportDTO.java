package com.diginamic.mission_note_de_frais.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ExpenseReportDTO {
  private Long id;
  private double amount;
  private StatusDTO status;
}
