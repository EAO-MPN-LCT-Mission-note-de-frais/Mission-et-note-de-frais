package com.diginamic.mission_note_de_frais.model.dto;


import java.time.LocalDate;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
   * The transports of the mission
   */
  private Set<Long> transportIds;

  /**
   * A summarized version of the expense report associated with this mission.
   */
  private SimpleExpenseReportDTO expenseReport;
  
  /**
   * The identifier for the MissionType
   */
  private Long missionTypeId;

  /**
   * Constructor for MissionDTO.
   *
   * @param id        the unique identifier for the mission
   * @param startDate the date the mission started
   * @param endDate   the date the mission ended
   * @param startTown the town where the mission started
   * @param endTown   the town where the mission ended
   * @param status      the current status of the mission
   */
  public MissionDTO(Integer id, LocalDate startDate, LocalDate endDate, String startTown, String endTown, StatusDTO status, Long missionTypeId) {
    this.id = id;
    this.startDate = startDate;
    this.endDate = endDate;
    this.startTown = startTown;
    this.endTown = endTown;
    this.status = status;
    this.missionTypeId = missionTypeId;
  }
  
  
}
