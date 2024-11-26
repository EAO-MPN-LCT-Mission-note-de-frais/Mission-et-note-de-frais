package com.diginamic.mission_note_de_frais.model.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class MissionDTO {
  /**
   * The unique identifier for the mission.
   */
  private Integer id;

  /**
   * The date the mission started.
   */
  private LocalDate startDate;

  /**
   * The date the mission ended.
   */
  private LocalDate endDate;

  /**
   * The town where the mission started.
   */
  private String startTown;

  /**
   * The town where the mission ended.
   */
  private String endTown;

  /**
   * The current status of the mission.
   */
  private StatusDTO status;

  /**
   * A summarized version of the expense report associated with this mission.
   */
  private SimpleExpenseReportDTO expenseReport;
}
