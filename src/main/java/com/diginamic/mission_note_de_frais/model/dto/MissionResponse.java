package com.diginamic.mission_note_de_frais.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@Accessors(chain = true)
public class MissionResponse {
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
    private ExpenseReportDTO expenseReport;

    /**
     * The identifier for the MissionType
     */
    private MissionTypeDTO missionType;
}
